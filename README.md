# MovieList

Listing movies search with google auth and offline history in kotlin.

## Info

Login and logout with Google account using the firebase platform, The user search by a keyword to the TMDB using the Retrofit HTTP client and then it list the search movies trough a reciclerView, when clicked in one item, it's possible to see the detail of the movie and it will save automatically in the phone usig the room persistence library.

## Libraries, APIs

+ **TMDB (The Movie DataBase)** - Rest API
+ **Firebase** - platform for google auth 
+ **Retrofit** - HTTP client API
+ **Picasso** - Image library
+ **Room** - Persistence library for SQLite

## Not taken into account

+ The UI
+ Testing
