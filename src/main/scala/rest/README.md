## Rest service exercise

### How to use
1. In the root of the dir run `sbt`
2. Inside sbt execute `runMain RestApp`

REST interface
---

**Retrieve a list of observations for a given carrier id**

* URL

    ### /observations?carrier_id={carrier_id}&observation_week={observation_week}

* Method

    GET

* Request Params

    - carrier_id - id of the carrier

    - оbservation_week - number of weeks since 01.01.2000

* Success Response - Json array containing the observations:
 ```json
    [
    	{
    	    "id": 2,
    	    "name": "firstFeature",
    	    "description": "feature description"
    	}
    ]
 ```