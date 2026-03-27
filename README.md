# Hang On, Man

A small Spring MVC + Thymeleaf hangman game with server-side rendering, in-memory state, and a cookie token that maps each player to their current round and simple stats.

## Updates

- Change server port to 5000 for Beanstalk deployment
- Add a light theme and allow the user to toggle between dark(default) and light
- Update python script change bucket from certificationexams.guru to certificationexams.pro
- Change the list of 15 words in the WorldBankService from Java related to AWS related terms
- Change title to Hang On, Chum!

## Run locally

```powershell
.\mvnw.cmd spring-boot:run
```

Then open `http://localhost:8080`.

## Run tests

```powershell
.\mvnw.cmd test
```

