---
mode: edit
description: Set or reset the Spring Boot console logging pattern in application.properties
---

# Beachcombers

Always work only on `#file:application.properties`.

Use `${input:mode}` as the mode selector.

If `${input:mode}` is `reset`:
- Remove the `logging.pattern.console` entry from `#file:application.properties`
- Do not change anything else

If `${input:mode}` is anything else or blank:
- In `#file:application.properties`, set the console logging pattern to exactly:

`logging.pattern.console: %n%d{HH:mm:ss} | %-5level | %msg`

Rules:
- Only modify `#file:application.properties`
- If `logging.pattern.console` already exists, replace it
- If it does not exist, add it
- Keep the change minimal
- After editing, briefly state whether the pattern was set or reset

