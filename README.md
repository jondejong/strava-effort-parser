# Strava Effort Parser
This app, as it stands, will take the 
spreadsheet you can get from Strava, and create new CSV files that map aggregates of the data by activity 
and by week and month. However, this is designed to be easily extensible to allow you to create a custom map
function to parse your own activities however you like.

### Getting your activities from Strava
Before using this, you will need your activities export from Strava. 
You can do that as described in [this article under "Bulk Export"](https://support.strava.com/hc/en-us/articles/216918437-Exporting-your-Data-and-Bulk-Export)

### To Run
You should get a file called activities.csv from Strava. Drop this file into the [data](https://github.com/jondejong/strava-effort-parser/tree/main/data)
folder. Then run the app. The easiest way to do this is to just use the `gradle run` command:
```./gradlew run```. The resulting output files will be written to the same data folder.

### To Extend
If you wish to aggregate your activities in some other way, you can do so by 
creating your own implementation of the [Mapper](https://github.com/jondejong/strava-effort-parser/blob/main/app/src/main/kotlin/strava/effort/parser/mapper/Mapper.kt).

Your custom mapper will need to do two things. First, it will need to be constructed with desired name of
the output file and the values for the header row of the out CSV. Secondly, you will need to implement the 
map function. This function takes a Collection of Activities and a start date. The start date is the Monday before the 
earliest activity. This function returns a Collection of Lists of Strings. Each item in the collection will become
a row in the corresponding output CSV file.

Lastly, you will need to add your new custom mapper to the mappers collection in the [App](https://github.com/jondejong/strava-effort-parser/blob/main/app/src/main/kotlin/strava/effort/parser/App.kt).
