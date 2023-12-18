[StudyFlow](#StudyFlow) •
[Request Review Strategy](#Request-Review-Strategy) •
[Branch Model](#Branch-Model) •
[Class diagram](#Class-diagram) •
[Time logging](#Time-logging) •
[Glossary](#Glossary)

<img align="right" src="https://github.zhaw.ch/storage/user/5895/files/d91add59-7608-4c86-96e2-5e44549aee3d" alt="StudyFlow-logo" height="120" width="120">

# StudyFlow

Welcome to StudyFlow, your personal study calendar. If any terminology is unclear, it can be found in the [glossary](#Glossary).

## Current functionality

An initial set of StudyReservations are loaded from a json file. This file remains the same throughout the whole use of the application. Additionally, a first file of modules is also being loaded.

### Dashboard

Upon starting the application, the first view is the dashboard. Here you can find an overview of your learning progress. In the top left corner, you’ll find your next three upcoming assessments.

In the bottom left corner, you’ll find the percentage of assessments that you already passed.
In the bottom middle, you find a graph which represents the different states of all the objectives of a Module. Currently, the color code is as follows:
  *	Orange / Red = DONE
  *	Green = DELAYED
  *	Yellow = PENDING
  
In the bottom right corner, you’ll find two numbers. The bigger number represents your current average over all modules, which already have at least one finished assignment. The average calculates the average of all assignments within a module and then the average over those modules, weighted according to the number of credits which can be obtained in a specific module. The smaller number to the right describes the possible average that can be achieved if every assessment that has not yet taken place will be calculated with a 5.5. We chose to use a 5.5, because we think if a customer learns with our studying platform it should be possible to achieve a 5.5 average over the upcoming assessments.

The top right corner is not yet implemented in the current version of the application. In the future there wil be a calendar view of the current day to see which objectives should be achieved today.

### Import View

If you press on the plus symbol you change to the import view. Here you can currently only click on the button “Modul hinzufügen”. It opens a new window which displays how a module will be added in the future. This functionality is not yet implemented in the current version.
Instead, you can choose using a new json import file. You can do this via the bottom of the import view. After choosing a file, it will be the new base on which the application will run.

### Calendar View

Here you can see the calendar in a week view. It imports all the assessments from the current base json and calculates all the studyTimes based on our algorithm. The algorithm evaluates all the assessments and assigns a priority to them. The two most important assessments will be alternatively studied for first. They are followed by the third and fourth most important assessments, and so on. The priority assignment depends on the following factors:
 *	Time until the assessment takes place
 *	Current average in the module
 *	The number of credits that you can achieve in this module
 *	How confident the user feels in this module

This is done like this to implement the learning method called "interleaving". It is said to be more efficient to have a lot of changes between the different tasks.
Another effect that is being taken into consideration is the "[spacing-effect](https://en.wikipedia.org/wiki/Spacing_effect)". This effect says that one should study in shorter time frames. This is why time frames of 20 minutes were chosen with a break of 10 minutes after a studyTime.

The earliest an assessment can be studied for is one month prior.

The calendar entries can be moved and opened, since this is a feature of the CalendarFx library. But the initially loaded content will not adapt to the changes performed in the calendar.

### Overview

On this page you get an overview over all the modules that are being loaded from the current base json file. You can click on any module and see all its properties.

### General information

Errors that occur during the running of our application will be logged to the console, but it will not be visible in the application itself.

This application was written in Java 17 and is compatible with up to Java 19. The project contains various JUnit tests to test the functionality and ensure that the program is working as intended. The JUnit version used in this project is `5.8.1`. The documentation for each test can be read inside the corresponding test class.

The installed gradle version is: `8.0.1.`

## Future improvements

The biggest improvement for the future is to make it adaptive to individual use. The moving of the entries should have an influence on the recalculating of all the studyTimes. It should also be possible to mark such a studyTime as delayed or done after the application was started. Furthermore, the studyReservation should be displayed in the calendar and be adaptable.

In the overview we will add the possibility to edit the modules. For example, a grade can be added to an assessment after it has passed, or an objective can be added. It is also possible to mark an objective as delayed or done here.

On the dashboard a legend will be added for the diagram, so that it is clearer what exactly is being represented.

# Request-Review-Strategy

Pull requests were titled with the name of the issue that was worked on including a title starting with 'PR:' so that it would automatically show in our kanban board. For example: `PR: issue #14 implemented feature xy`. 

First, the branches were merged onto the development branch. The pull requests onto the development branch had to be reviewed and approved by two team members. From there were updating pull requests created onto the main branch. Those pull requests had to be approved by all the members of the team.

The main branch and the development branch are protected and cannot be directly changed.

## Pull Requests

Here you can find two pull requests with some conversations:

[PR #59](https://github.zhaw.ch/PM2-IT22taWIN-muon-pero-pasu/team03-StackOverflow-projekt2-studyflow/pull/59)

[PR #65](https://github.zhaw.ch/PM2-IT22taWIN-muon-pero-pasu/team03-StackOverflow-projekt2-studyflow/pull/65#pullrequestreview-43600)

# Branch Model

A separate branch was created for each feature, implementation or bugfix. Those branches were created following the following guidelines:

Type of work done / what was done:
types:
- Bugfix
- Feature
- Documentation

Rules: Branch names in english, lower case letters, spaces replaced with '_'. For example: `bugfix/dashboard_loading_failed_fixed`

Pull requests are also documented in the [Kanban board](https://github.zhaw.ch/PM2-IT22taWIN-muon-pero-pasu/team03-StackOverflow-projekt2-studyflow/projects/1) of the project.

## Labels

We have used following labels for our issues:

* bug
* controller
* documentation
* enhancement
* gui/view
* help wanted
* implementation
* logic
* test

# Class diagram

Here you can find our class diagram. We chose not to display custom Exception classes. A legend can be found [here](#Class-diagram-legend).

## Overview

![image](https://github.zhaw.ch/storage/user/5895/files/f084d56c-28b8-4664-91fb-181c03b95895)

## Controllers (blue)

![controller_class_diagram](https://github.zhaw.ch/storage/user/5895/files/683c7afe-b2e3-4aee-8afa-98d6799e26d8)

## Models (yellow)

![model_class_diagram](https://github.zhaw.ch/storage/user/5895/files/cb2646c2-be86-4124-a738-da0b828ac4d4)

## StudyManagement (pink)

![rest_class_diagram](https://github.zhaw.ch/storage/user/5895/files/1cf80c97-793a-4a9e-8436-327f5fc721ed)

## Class diagram legend

![image](https://github.zhaw.ch/storage/user/5895/files/4223ffd8-b3c2-4bd4-8674-d5d96b0c1284)
![image](https://github.zhaw.ch/storage/user/5895/files/10ab3139-de02-4ea8-88fd-bae44fd375ba)

## Explanation

Overall, the project demonstrates a strong focus on maintainability, sustainability and readability. In order to achieve that, we incorporated several key practices.

In terms of maintainability, the separation of controllers and models into distinct packages provides a clear and organized structure. This separation makes it easier for developers to locate specific classes for making modifications or adding new features. By reducing the complexity of the codebase, the team has ensured that future maintenance and updates can be carried out more efficiently.

The introduction of an interface for `UserData` showcases the sustainability of the project. By decoupling the specific data retrieval implementation from the rest of the codebase, the application becomes adaptable to different data sources or fetching methods. Currently, the data is read from JSON files and processed in the `UserDataManager` class, which implements the `UserData` interface. However, this design choice allows for seamless integration of alternative data retrieval methods in the future, such as a database or an external API. This flexibility ensures the longevity and sustainability of the application, allowing it to evolve with changing requirements and technological advancements.

Readability has been a primary consideration throughout the project, as evident from the deliberate naming conventions adopted by the development team. By using descriptive and meaningful class names, the code becomes self-explanatory, enhancing the readability of the entire application. Clear and intuitive naming not only aids current developers in understanding the purpose and functionality of various components but also eases the onboarding process for new team members. This attention to readability reduces the cognitive load required to comprehend the codebase and promotes effective collaboration within the development team.

By prioritizing maintainability, sustainability and readability, the project has established a solid foundation for long-term success. The deliberate architectural decisions, such as package separation, interface implementation, and naming conventions, contribute to a codebase that is maintainable, adaptable, and comprehensible. These practices empower the development team to make changes, extend functionality, and scale the application with ease. Furthermore, they lay the groundwork for efficient collaboration, knowledge transfer, and continued improvement, ensuring the project's longevity and success in the face of evolving requirements and future enhancements.

# Time logging

Here you can find the issue where the time spent working on this project is being logged by each team member: [#3](https://github.zhaw.ch/PM2-IT22taWIN-muon-pero-pasu/team03-StackOverflow-projekt2-studyflow/issues/3).

# Glossary

## StudyReservation

This is the time in which the tool is allowed to freely book you any entries into your calendar.

## StudyTime

The term studyTime describes a single calendar entry for studying. A studyTime always has a corresponding objective which should be studied for during this time. In the code a studyTime is called an Entry.

## Modules

Modules represent a subject at a university. They consist of a title, number of credits, a personal progress (is an estimation of how secure the student feels in this subject) and of a list of assessment. This list contains all the assessments that will be written over the whole semester.

## Assessment

An assessment represents an exam. It consists of a title, a boolean variable to mark whether the assessment is finished (an assessment counts as finished when it has a grade and not when it is passed), a grade and a list of objectives. Since the assessments are a subclass of `TimeFrame`, they also have a start and an end date.

## Objectives

The objectives represent all the topics that must be studied for one assessment. One objective consists of a title, a description, a finish date, which signals until when this topic must be learned, and a state. There are following three states:
 * `DONE`: This marks a topic as done and needs no longer to be studied
 * `DELAYED`: This shows that the studyTime corresponding to the objective has been postponed at least once
 * `PENDING`: This is the initial state of an objective.

## Grade

A grade consists of the actual grade and a weight. The grade is a number between 1 and 6 (0 if no grade has been set for an assessment) according to the Swiss Grading System. The weight is a number between 1 and 100 (0 if no grade has been set for an assessment). The weight represents the importance of the grade in the context of the module in percent.
