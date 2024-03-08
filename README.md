## Search Demo
<p align="center">
<img src="intro_1.png" width="200"/> <img src="intro_2.png" width="200"/> <img src="intro_3.png" width="200"/>
</p>

## Overall check list
- [x] The [CurrencyListFragment](app/src/main/java/com/cryptoassignment/ui/currency/CurrencyListFragment.kt)  is expected to receive an ArrayList of CurrencyInfo
  objects to create the UI.
- [x] The [DemoActivity](app/src/main/java/com/cryptoassignment/ui/demo/DemoActivity.kt) should provide two datasets, Currency List A and Currency List B,
  which contain CurrencyInfo objects to be queried from the local database.
- [x] The DemoActivity should also include five buttons for demonstrating various
  functionalities: [DemoControlPanelFragment](/app/src/main/java/com/cryptoassignment/ui/demo/DemoControlPanelFragment.kt)
    - [x] The first button is responsible for clearing the data in the local database.
    - [x] The second button is used to insert the data into the local database.
    - [x] The third button changes the CurrencyListFragment to use Currency List A -
      Crypto.
    - [x] The fourth button changes the CurrencyListFragment to use Currency List B -
      Fiat.
    - [x] The fifth button displays all CurrencyInfo objects that can be purchased from
      Currency List A and B.
- [x] Additionally, the CurrencyListFragment should provide a search feature that can be
  cancelled when the user clicks the back or close button.
- [x] Furthermore, the CurrencyListFragment should include an empty view for displaying
  an empty list.
- [x] Lastly, it is crucial that all IO operations, including database or network access, are
  not performed on the UI Thread to ensure smooth execution.

## Assignment Overview

There is only one module ([app](app)) without any modularization setup as the requirement
is less at this level. but overall split into three levels below for the future.
- Single module setup with clear package naming (We are here)
    - base: common tools or base impl
    - data: raw data from remote / local
    - ui: app page
    - di: dependency injection set up

- Modularization (future plan)

  Moving into this level when reach those conditions below.
    - Production level.
    - More than 10 dev work in this project.
    - The project age will be more than 2 years.

  Solution:
    - mobile : application with dagger injection management
    - modules-core : basic core could share all module use. It also contains network & store handle
    - modules-ui : basically anything relate to screen show to user will put in this
        - core : base presenter, base activity & fragment if it needed etc..
        - feature-B
        - feature-A

- Single Activity approach (future plan)

  Having only one MainActivity with multiple fragments in order to have better management such like global message reminder.

## Local Data
- app/data/repo/currency :`CurrencyRepo`
    - Response for data converter & local data set

    
## Unit / UI test
- Database query test: [CurrencyDaoTest](app/src/androidTest/java/com/cryptoassignment/local/localcurrency/CurrencyDaoTest.kt)
    - [x] A coin will match if:
      The coin’s name (e.g. Bitcoin) starts with the search term
        - Example 1: Query: `foo`
            - Matches these coin names: Foobar
            - Does not match: Barfoo
        - Example 2: Query: `Ethereum`
            - Matches these coin names: Ethereum, Ethereum Classic
            - Does not match: -
- OR -
    - [x]  The coin’s name contains a partial match with a ‘ ’ (space) prefixed to the search term
      - Example: Query: `Classic`
          - Matches these coin names: Ethereum Classic
          - Does not match: Tronclassic
    - OR -
      - The coin’s symbol starts with the search term
                - Example: Query: `ET`
                    - Matches these symbols:

- [CurrencyRepoImplTest](app/src/test/java/com/cryptoassignment/data/repo/currency/CurrencyRepoImplTest.kt)
- [CurrencyListViewModelTest](app/src/test/java/com/cryptoassignment/ui/currency/CurrencyListViewModelTest.kt)

## Considerations
- Error handling
- Maybe there is a A/B testing for search conditions that want to make it dynamic for the sql query. 
  - It would probability need to wrap the sql query text out & being able to pass in DAO with different combination.
  - SimpleSQLiteQuery to manage different search conditions maybe also is another direction. 
- Constant key definition
- Leakcanary & Crashlytics
- Lint check
- Design component: At least we can define text style base on the current requirement
- Consider having an UseCase pattern if we have more complex core business logic
- CI / CD set up
- Landscape design
- More unit test 

## Tech usage
- Arch : MVVM (Clean Arch)
- DI : Dagger Hilt
- Code language : Kotlin
- Design Patterns: Repository pattern

## Reference
[(Search demo)](challenge.pdf)