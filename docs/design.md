# Design notes

## Why cents

Money is stored as `long` cents. A `double` cannot hold 0.10 exactly, so sums
of many small amounts drift. With cents, every operation is exact.

## Errors

Business errors are checked exceptions (`InsufficientFundsException`,
`AccountNotFoundException`, `AccountFrozenException`). The caller must decide
what to do. Programming errors, such as a negative amount, are
`IllegalArgumentException`.

## Transaction history

Each account keeps the list of its transactions in order. The list returned to
callers is read-only.
