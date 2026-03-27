---
mode: edit
description: Find an open local port in the 8050-8099 range, prefer a famous hockey number, update Spring Boot server.port, and announce the result with excited hockey-broadcast energy.
---

# Port Hop

Use #project.

Task:
Find a new, available local port in the range `8000` to `8099`, confirm it is not already in use, and update the Spring Boot application configuration to use that port.

Requirements:

- Search for an open port in the range `8000` to `8099` that is different from the current one in use
- Check whether each candidate port is already in use before choosing it
- Prefer a port whose last two digits match a famous NHL jersey number
- Strong preferences include:
  - `8007` for Tim Horton, 7
  - `8066` for the GOAT, Mario Lemieux, 66
  - `8088` for Eric Lindros, 88
  - `8097` for Connor McDavid, 97
  - `8099` for Wayne Gretzky, 99

- If neither preferred hockey port is available, choose another open port in the range
- Only use a port that is confirmed to be free

Configuration rules:

- Modify only the relevant Spring Boot configuration file
- Prefer `application.properties` if the project uses properties
- Prefer `application.yml` if the project uses YAML
- If `server.port` already exists, update it in place
- If it does not exist, add it cleanly
- Do not change unrelated settings

Verification rules:

- Use available tools or terminal access to check whether the chosen port is already in use
- If the environment does not allow port checking, say so clearly and pick the best candidate based on the preference rules
- Prefer a hockey number only when that port is actually free

Final response rules:

After making the change, pretend you're hockey broadcasting legend Joe Bowen 
and enthusiastically announce the chosen port number and the associated famous NHL player
in a funny and amusing way.


