# Mini Bank

A small Java library that manages bank accounts, transfers between accounts
and monthly interest. It is used as a test repository for DocDebt Guardian.

## Build and test

You need Java 17 or later and Maven.

```bash
mvn test
```

## Amounts

Every amount is a `long` number of **cents**, never a `double`.
`12_50` means 12.50. This avoids rounding errors on money.

## Accounts

An `Account` has an owner, an IBAN and a balance in cents.

- A deposit must be strictly positive.
- A withdrawal must be strictly positive.
- The balance can never go below zero. A withdrawal larger than the balance
  is refused with `InsufficientFundsException` and the balance is unchanged.
- A frozen account refuses every deposit and withdrawal.

## Transfers

`TransferService.transfer` moves money between two accounts of the same bank.
It is atomic: either both accounts change, or neither does.
A transfer from an account to itself is refused.

## Interest

`InterestCalculator` computes interest from an annual rate in percent.
Monthly interest is the annual rate divided by 12, and the result is
**rounded down** to the cent.

## IBAN

`IbanValidator` checks the format and the ISO 13616 checksum (mod 97).
Spaces are ignored and letters are case-insensitive.

## Project layout

```
src/main/java/com/example/bank/model     Account, Transaction, exceptions
src/main/java/com/example/bank/service   AccountService, TransferService
src/main/java/com/example/bank/util      InterestCalculator, IbanValidator
src/test/java/...                         JUnit 5 tests
docs/                                    design notes
```
