# Contributing

1. Create a branch from `main`.
2. Keep every public method documented with Javadoc: a description,
   one `@param` per parameter, `@return` for non-void methods and one
   `@throws` per declared exception.
3. Add or update the JUnit tests.
4. Run `mvn test` before opening a pull request.

Pull requests are reviewed by DocDebt Guardian, which flags Javadoc that no
longer matches the code.
