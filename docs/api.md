# Public API Documentation

Open-Elevation's API is extremely simple -- after all, it fits a single, specific, simple task. There is **only one endpoint**, which is documented here.

## `GET /api/v1/lookup`

Returns ("looks up") the elevation at one or more `(latitude,longitude)` points.

The GET API is limited to **1024** bytes in the request line. If you plan on making large requests, consider using the POST api.

### Parameters:

* **`locations`**: List of locations, separated by `|` in `latitude, longitude` format, similar to the Google Elevation API.

### Response format

A JSON object with a single list of results, in the `results` field is returned. Each result contains `latitude`, `longitude` and `elevation`. The results are in the same order as the request parameters. **Elevation is in meters**.

If there is no recorded elevation at the provided coordinate, sea level (0 meters) is returned.

```json
{
	"results":
	[
		{
			"latitude": ...,
			"longitude": ...,
			"elevation": ...
		},
		...
	]
}
```


### Example:

#### Request

```
curl 'https://api.open-elevation.com/api/v1/lookup?locations=10,10|20,20|41.161758,-8.583933'
```

#### Response

```json
{
   "results":
   [
      {
         "longitude":10.0,
         "elevation":515,
         "latitude":10.0
      },
      {
         "longitude":20.0,
         "elevation":545,
         "latitude":20.0
      },
      {
         "latitude":41.161758,
         "elevation":117,
         "longitude":-8.583933
      }
   ]
}
```


## `POST /api/v1/lookup`

Returns ("looks up") the elevation at one or more `(latitude,longitude)` points.

The POST API currently has no limit

### Parameters:

* A JSON (and respective headers) is required with the format:
```
{
    "locations":
    [
        {
            "latitude": ...,
            "longitude": ...
        },
        ...
}
```


### Response format

A JSON object with a single list of results, in the `results` field is returned. Each result contains `latitude`, `longitude` and `elevation`. The results are in the same order as the request parameters. **Elevation is in meters**.

If there is no recorded elevation at the provided coordinate, sea level (0 meters) is returned.

```json
{
	"results":
	[
		{
			"latitude": ...,
			"longitude": ...,
			"elevation": ...
		},
		...
	]
}
```


### Example:

#### Request

```
curl -X POST \
  https://api.open-elevation.com/api/v1/lookup \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
	"locations":
	[
		{
			"latitude": 10,
			"longitude": 10
		},
		{
			"latitude":20,
			"longitude": 20
		},
		{
			"latitude":41.161758,
			"longitude":-8.583933
		}
	]

}'
```

#### Response

```json
{
   "results":
   [
      {
         "longitude":10.0,
         "elevation":515,
         "latitude":10.0
      },
      {
         "longitude":20.0,
         "elevation":545,
         "latitude":20.0
      },
      {
         "latitude":41.161758,
         "elevation":117,
         "longitude":-8.583933
      }
   ]
}
```
