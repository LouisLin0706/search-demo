# Search Demo

<p align="center">
<img src="intro_1.png" width="200"/> <img src="intro_2.png" width="200"/> <img src="intro_3.png" width="200"/>
</p>

## Overall Checklist

- [x] The **CurrencyListFragment** ([CurrencyListFragment.kt](app/src/main/java/com/cryptoassignment/ui/currency/CurrencyListFragment.kt)) is expected to receive an `ArrayList` of `CurrencyInfo` objects to create the UI.
- [x] The **DemoActivity** ([DemoActivity.kt](app/src/main/java/com/cryptoassignment/ui/demo/DemoActivity.kt)) should provide two datasets, **Currency List A** and **Currency List B**, which contain `CurrencyInfo` objects to be queried from the local database.
- [x] The **DemoActivity** should include five buttons for demonstrating various functionalities in the **DemoControlPanelFragment** ([DemoControlPanelFragment.kt](app/src/main/java/com/cryptoassignment/ui/demo/DemoControlPanelFragment.kt)):
    - [x] The first button clears the data in the local database.
    - [x] The second button inserts data into the local database.
    - [x] The third button changes the **CurrencyListFragment** to use **Currency List A** - Crypto.
    - [x] The fourth button changes the **CurrencyListFragment** to use **Currency List B** - Fiat.
    - [x] The fifth button displays all `CurrencyInfo` objects that can be purchased from **Currency List A** and **B**.
- [x] The **CurrencyListFragment** provides a search feature that can be canceled when the user clicks the back or close button.
- [x] The **CurrencyListFragment** includes an empty view for displaying when the list is empty.
- [x] All IO operations, including database or network access, are not performed on the UI thread to ensure smooth execution.

## Assignment Overview

The existing codebase remains unchanged, but improvements have been made by introducing Jetpack Compose:

## **🚀 Jetpack Compose Improvements**

Instead of revamping the whole app structure from the root node, the existing code has been migrated to Jetpack Compose with minimal impact. We are essentially adding two screens as Compose:

- **CurrencyListFragment2**: Host of the Compose, **CurrencyListScreen**: Content.
- **DemoControlPanel2Fragment**: Host of Compose, **DemoControlPanelScreen**: Content.

### **Key Practices Implemented:**
- **Imperative MVI**: A Model-View-Intent architecture is used to manage state and actions.
- **Preview**: Composable previews are utilized for UI testing and visualization.
- **Pure Composable Functions**: The use of pure composable functions for better reusability and testing.

## **TODO**
- **Testing on Compose**: Implement unit and UI tests for the new composables.
- **Revamp the whole structure** to not rely on fragments; allow Compose to manage navigation.


## Local Data

- **Currency Repository**:
    - Located at `app/data/repo/currency`: `CurrencyRepo`
        - Responsible for data conversion and local data management.

## Unit / UI Tests

- **Database Query Test**: [CurrencyDaoTest](app/src/androidTest/java/com/cryptoassignment/local/localcurrency/CurrencyDaoTest.kt)
    - [x] A coin will match if:
        - The coin's name (e.g., Bitcoin) starts with the search term.
            - Example 1: Query: `foo`
                - Matches: Foobar
                - Does not match: Barfoo
            - Example 2: Query: `Ethereum`
                - Matches: Ethereum, Ethereum Classic
                - Does not match: -
    - OR -
        - The coin's name contains a partial match with a space prefixed to the search term.
            - Example: Query: `Classic`
                - Matches: Ethereum Classic
                - Does not match: Tronclassic
        - OR -
            - The coin's symbol starts with the search term.
                - Example: Query: `ET`
                    - Matches these symbols.

- Additional Tests:
    - [CurrencyRepoImplTest](app/src/test/java/com/cryptoassignment/data/repo/currency/CurrencyRepoImplTest.kt)
    - [CurrencyListViewModelTest](app/src/test/java/com/cryptoassignment/ui/currency/CurrencyListViewModelTest.kt)

## Considerations

- Implement error handling.
- Consider A/B testing for search conditions to make SQL queries dynamic.
    - This may require wrapping SQL query text and allowing different combinations to be passed to DAO.
    - Using `SimpleSQLiteQuery` to manage different search conditions could also be a viable approach.
- Define constant keys.
- Integrate LeakCanary & Crashlytics for monitoring.
- Conduct lint checks.
- Establish design components: Define text styles based on current requirements.
- Consider implementing a UseCase pattern if more complex business logic is needed.
- Set up CI/CD pipelines.
- Ensure landscape design compatibility.
- Increase unit test coverage.

## Tech Stack

- **Architecture**: MVVM (Clean Architecture)
- **Dependency Injection**: Dagger Hilt
- **Programming Language**: Kotlin
- **Design Patterns**: Repository Pattern

## References

- [Search Demo Documentation](challenge.pdf)