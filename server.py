import bottle
from bottle import route, run, request

from gdal_interfaces import GDALTileInterface

interface = GDALTileInterface('data/', 'data/summary.json')
interface.create_summary_json()

def get_elevation(location_with_comma):
    try:
        lat, lng = [float(i) for i in location_with_comma.split(',')]
    except:
        return bottle.HTTPResponse(status=400, body={'error': 'Bad parameter format "%s".' % location_with_comma})

    try:
        elevation = interface.lookup(lat, lng)
    except:
        return bottle.HTTPResponse(status=404, body={'error': 'No such coordinate (%s, %s)' % (lat, lng)})

    return {
        'latitude': lat,
        'longitude': lng,
        'elevation': elevation
    }

@route('/api/v1/lookup')
def lookup():
    locations = request.query.locations

    if not locations:
        return bottle.HTTPResponse(status=400, body={'error': '"Locations" is required.'})

    return {'results': [get_elevation(l) for l in locations.split('|')]}

#run(host='0.0.0.0', port=8080)
run(host='0.0.0.0', port=8080, server='gunicorn', workers=4)