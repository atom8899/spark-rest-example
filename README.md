# spark-rest-example


## Build

Run Tests
```bash
cd /some/path/spark-rest-example/
gradle check
```

Build Shadow Jar
```bash
gradle shadowJar
```

# Run

```bash
gradle shadowJar
cd /build/libs
java -jar -port 9000 spark-rest-example-{VERSION}-all.jar
```

# Submitting Rates

```bash
curl -X POST \
  http://localhost:9000/parking/import/rates \
  -H 'accept: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
    "rates": [
        {
            "days": "mon,tues,thurs",
            "times": "0900-2100",
            "price": 1500
        },
        {
            "days": "fri,sat,sun",
            "times": "0900-2100",
            "price": 2000
        },
        {
            "days": "wed",
            "times": "0600-1800",
            "price": 1750
        },
        {
            "days": "mon,wed,sat",
            "times": "0100-0500",
            "price": 1000
        },
        {
            "days": "sun,tues",
            "times": "0100-0700",
            "price": 925
        }
    ]
}'
```
Reponse
```json
{"status":"Success","message":"Rates Created"}
```

# Exposing existing Rates in the system
```bash
curl -X GET \
  http://localhost:9000/parking/rates \
  -H 'accept: application/json'
```

Response
```json
{
   "status":"Success",
   "message":"Current Rates",
   "data":{
      "rates":[
         {
            "id":1,
            "times":"0900-2100",
            "price":1500,
            "days":"mon,tues,thurs",
            "validations":[

            ]
         },
         {
            "id":2,
            "times":"0900-2100",
            "price":2000,
            "days":"fri,sat,sun",
            "validations":[

            ]
         },
         {
            "id":3,
            "times":"0600-1800",
            "price":1750,
            "days":"wed",
            "validations":[

            ]
         },
         {
            "id":4,
            "times":"0100-0500",
            "price":1000,
            "days":"mon,wed,sat",
            "validations":[

            ]
         },
         {
            "id":5,
            "times":"0100-0700",
            "price":925,
            "days":"sun,tues",
            "validations":[

            ]
         }
      ]
   }
}
```
# Evaluating Rate for Time
```bash
curl -X GET \
  'http://localhost:9000/parking/availability?start_time=2015-07-01T07%3A00%3A00Z&end_time=2015-07-01T12%3A00%3A00Z' \
  -H 'accept: application/json' \
  -H 'cache-control: no-cache'
```

response
```json
{"status":"Success","message":"Rate Found","data":1750}
```