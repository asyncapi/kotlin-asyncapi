# Contributing

The kotlin-asyncapi project is open and grateful for contributions. You can help us by reporting/fixing issues and adding new features. 

Please consider our vision for this project when suggesting/adding new features. We want to provide code-first tools for Kotlin microservices that make it easy for developers to document event-based APIs.

Familiarize yourself with the [project architecture](./ARCHITECTURE.md) and design your contribution accordingly.

## Code Style

Please follow the [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).

## Commit Messages

Please follow [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/).

## Testing

Please update and add new tests to reflect your code changes. Pull requests will not be accepted if they are failing on GitHub actions.

## Documentation

Please update the [docs](README.md) accordingly so that there are no discrepancies between the API and the documentation.

## Developing

- fork the project
- create a new branch from `dev` (pattern `chore/feat/docs/refactor.../<branch-name>` e.g. `feat/add-annotation-processing`)
- open a PR with `dev` as base branch
- fill out the PR template
- provide a descriptive PR title (pattern `chore/feat/docs/refactor...:<pr title>` e.g. `feat: Add annotation processing`)
- wait for 1 CODEOWNER + 1 MAINTAINER review
- squash and merge your PR
- your commit will be part of the next release
