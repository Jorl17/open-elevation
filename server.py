import json

import bottle
from bottle import route, run, request, response

from gdal_interfaces import GDALTileInterface

interface = GDALTileInterface('data/', 'data/summary.json')
interface.create_summary_json()

class InternalException(ValueError):
    pass


def get_elevation(location_with_comma):
    try:
        lat, lng = [float(i) for i in location_with_comma.split(',')]
    except:
        raise InternalException(json.dumps({'error': 'Bad parameter format "%s".' % location_with_comma}))

    try:
        elevation = interface.lookup(lat, lng)
    except:
        raise InternalException(json.dumps({'error': 'No such coordinate (%s, %s)' % (lat, lng)}))

    return {
        'latitude': lat,
        'longitude': lng,
        'elevation': elevation
    }

@route('/api/v1/lookup')
def lookup():
    locations = request.query.locations

    if not locations:
        response.status = 400
        response.content_type = 'application/json'
        return json.dumps({'error': '"Locations" is required.'})

    try:
        return {'results': [get_elevation(l) for l in locations.split('|')]}
    except InternalException as e:
        response.status = 400
        response.content_type = 'application/json'
        return e.args[0]

#run(host='0.0.0.0', port=8080)
run(host='0.0.0.0', port=8080, server='gunicorn', workers=4)