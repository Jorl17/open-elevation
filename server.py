import json

import bottle
from bottle import route, run, request, response, hook

from gdal_interfaces import GDALTileInterface


class InternalException(ValueError):
    """
    Utility exception class to handle errors internally and return error codes to the client
    """
    pass


"""
Initialize a global interface. This can grow quite large, because it has a cache.
"""
interface = GDALTileInterface('data/', 'data/summary.json')
interface.create_summary_json()


def get_elevation(lat, lng):
    """
    Get the elevation at point (lat,lng) using the currently opened interface
    :param lat: 
    :param lng: 
    :return:
    """
    try:
        elevation = interface.lookup(lat, lng)
    except:
        return {
            'latitude': lat,
            'longitude': lng,
            'error': 'No such coordinate (%s, %s)' % (lat, lng)
        }

    return {
        'latitude': lat,
        'longitude': lng,
        'elevation': elevation
    }


@hook('after_request')
def enable_cors():
    """
    Enable CORS support.
    :return: 
    """
    response.headers['Access-Control-Allow-Origin'] = '*'
    response.headers['Access-Control-Allow-Methods'] = 'PUT, GET, POST, DELETE, OPTIONS'
    response.headers['Access-Control-Allow-Headers'] = 'Origin, Accept, Content-Type, X-Requested-With, X-CSRF-Token'


def lat_lng_from_location(location_with_comma):
    """
    Parse the latitude and longitude of a location in the format "xx.xxx,yy.yyy" (which we accept as a query string)
    :param location_with_comma: 
    :return: 
    """
    try:
        lat, lng = [float(i) for i in location_with_comma.split(',')]
        return lat, lng
    except:
        raise InternalException(json.dumps({'error': 'Bad parameter format "%s".' % location_with_comma}))


def query_to_locations():
    """
    Grab a list of locations from the query and turn them into [(lat,lng),(lat,lng),...]
    :return: 
    """
    locations = request.query.locations
    if not locations:
        raise InternalException(json.dumps({'error': '"Locations" is required.'}))

    return [lat_lng_from_location(l) for l in locations.split('|')]


@route('/api/v1/lookup', method=['OPTIONS', 'GET'])
def lookup():
    """
    Main GET method.
    :return: 
    """
    try:
        locations = query_to_locations()
        return {'results': [get_elevation(lat, lng) for (lat, lng) in locations]}
    except InternalException as e:
        response.status = 400
        response.content_type = 'application/json'
        return e.args[0]

#run(host='0.0.0.0', port=8080)
run(host='0.0.0.0', port=8080, server='gunicorn', workers=4)