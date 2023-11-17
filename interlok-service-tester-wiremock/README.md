# interlok-service-tester-wiremock

Pre V5 the Service Tester Wiremock is using [WireMock](https://github.com/wiremock/wiremock). Since V5, Interlok is using Jetty 10 which makes it incompatible with WireMock (at least for now).
For this reason the Service Tester V5 is using [MockServer](https://github.com/mock-server/mockserver) instead of WireMock.
Using MockServer has bruoght some breaking change with the mock config but we kept it to a minimum for basic configurations.

- Request payload in the `__files` dir are not working anymore.
- The mappings json format in the mappings dir are a bit different:
 - request -> httpRequest
 - url -> path
 - response -> httpResponse
 - headers format changed
 - bodyFilename -> body in a json format.
USe lombok in WireMockHelper

Old format:

```json
{
  "request" : {
    "url" : "/jokes/random",
    "method" : "GET"
  },
  "response": {
    "status": 200,
    "body": "{ \"icon_url\" : \"https://assets.chucknorris.host/img/avatar/chuck-norris.png\", \"id\" : \"E-H-5-TlS2msprVLgr40cQ\", \"url\" : \"\", \"value\" : \"Why did the chicken cross the road? Because it was afraid to be on the same side as Chuck Norris.\" }",
    "headers": {
        "Content-Type": "application/json"
    }
  }
}
```

New format:

```json
{
  "httpRequest" : {
    "path" : "/jokes/random",
    "method" : "GET"
  },
  "httpResponse": {
    "statusCode": 200,
    "body": { "icon_url" : "https://assets.chucknorris.host/img/avatar/chuck-norris.png", "id" : "E-H-5-TlS2msprVLgr40cQ", "url" : "", "value" : "Why did the chicken cross the road? Because it was afraid to be on the same side as Chuck Norris." },
    "headers": {
        "content-type": [ "application/json" ]
    }
  }
}
```
