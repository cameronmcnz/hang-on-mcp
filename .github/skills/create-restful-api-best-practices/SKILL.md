---
name: RESTful API Creation and Design Best Practices
description: Use this skill when the user needs to build a REST API or create a RESTful API.
---

# RESTful API Design and Creation Best Practices

Use this skill whenever the user asks to "build a REST API" or "create a RESTful API".

## Rules

Use nouns, not verbs when naming endpoints and forming the path.

Do NOT create endpoints or paths like:
- /add
- /createUser
- /getItems

DO create endpoints and resource based paths by using nouns as the resource names, such as:
- /sum
- /users
- /orders

## Parameter naming
Use clear and descriptive names for query parameters

Good:
- ?numerator=10&denominator=2
Avoid:
- ?a=40&b=2


## Return simple types directly

When the result is a simple type like:
- int
- double
- String
- boolean
Return the raw value directly, not as a ResponseBody and not wrapped in JSON

Implement each endpoint in its own method.
For Quarkus apps, use the @Provider annotation when needed as we likely will create native builds.










