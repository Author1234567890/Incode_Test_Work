### Build:
`./gradlew build`

### Run:
`./gradlew bootRun`

### Usage:
`curl -X POST http://localhost:8080/filter -H "Content-Type: application/json" --data "@body.json"`

### Assignment:
1. Generate JSON file where you will have array of elements. 
Each one of them will have value as a string parameter and array of filters assigned to it.
Each filter will be described with "filterGroupId", "filterId" as well as the optional parameters.
2. Using annotations generate three filter methods where each filter will be described with "filterGroupId", and "filterId", as well as the parameters accepted.
   - one filter should detect Cyrillic and Greek letters in Latin text and convert those letters (no input parameter)
   - one filter should detect regex pattern in the element value and remove matched value from the   original string (one parameter associated with the filter, regex pattern)
   - one filter will detect regex pattern in the element value and replace the matched value with another   one provided as parameter (two parameters in this case associated with the filter)
3. Load all the filters on system start up and keep them in memory
4. Load the JSON file and process it in such a way that for each element of the array you will apply the associated set of filters.
5. Print the original and filtered value.

Example input:
```json
{
  "elements" : [
    {
      "value" : "This is some Random Text",
      "filters" : [
        {
          "filterGroupId": "group1",
          "filterId": "filter1",
          "parameters": ["PARAM1", "PARAM2"]
        },
        {
          "filterGroupId": "group1",
          "filterId": "filter2",
          "parameters": ["PARAM1"]
        }
      ]
    }
  ]
} 
 ```