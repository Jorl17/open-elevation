# Public API Documentation

Open-Elevation's API is extremely simple -- after all, it fits a single, specific, simple task. There is **only one endpoint**, which is documented here.

## `GET api/v1/lookup`

Returns ("looks up") the elevation at one or more `(latitude,longitude)` points.

### Parameters:

* **`locations`**: List of locations, separated by `|` in `latitude, longitude` format, similar to the Google Elevation API.

### Response format

A JSON object with a single list of results, in the `results` field is returned. Each result contains `latitude`, `longitude` and `elevation`. The results are in the same order as the request parameters. **Elevation is in meters**.

If there is no recorded elevation at the provided coordinate, sea level (0 meters) is returned.

```
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
curl https://api.open-elevation.com/api/v1/lookup\?locations\=10,10\|20,20\|41.161758,-8.583933
```

#### Response

```
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