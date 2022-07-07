SoundMemo
=========
SoundMemo will be a self-reminder mobile app designed for people with dementia, with the aim of reducing the global burden of disease caused by dementia and helping people with dementia better fight the disease.

# Project Description:
SoudMemo will mainly provide technical solutions for the deep forgetfulness of most dementia patients. Patients can get text, ringtone, or voice reminders through the reminders pre-set by the caregiver.

# Bulid Status
This application is still in development and currently only includes the basic framework. 07.7.2022

# Gulidelines:
SoundMemo will use JAVA 8 as the main development language and use the integrated IDE - Android studio (version 2021.2.1) as the main development compiler. The development team will follow an agile development process and use Gitlab for collaborative work. Regarding databases, database developers will use services such as the real-time database provided by Google's Firebase app development platform to build 
database parts such as user information. Regarding user interface design, UI designers will use Figma to design the detailed parts of the user interface. And the application and user interface will be adapted to four-inch and six-inch mainstream mobile handheld devices running the Android OS version 7.0 (API 24, Nougat) and newer, and 10-inch mainstream tablets also running the Android OS version 7.0 (API 24, Nougat) and newer. 

# Installation

**Start by cloning the repository with the following command in the desireed folder**:

**Clone with SHH
```bash
git@csil-git1.cs.surrey.sfu.ca:cmpt276-group8/cmpt276-project.git
```

**Clone with HTTP
```bash
https://csil-git1.cs.surrey.sfu.ca/cmpt276-group8/cmpt276-project.git
```
## File describe:

**add_page.xml**
This file contains the basic user interface design for the caregiver to set reminders.

**calender_page.xml**
This file includes the basic user interface design for the calendar component and the janitor viewing interface.

**incoming_act_page.xml**
This file contains the basic user interface design for the patient reminder interface.
    **Some features still unimplemented.**

**login_page.xml**
This file includes the basic user interface design of the login interface.

**patient_calender_page.xml**
This file is the basic page that the patient can't do anything with, and it contains the basic user interface design for that page.

**MainActivity.java**
This file contains the API for verifying account and password functions with Google account login. 
    **Still not in line with the actual use of the scene.**
## Authors
Group 08

Team Member:

HaoxiangYuan(Carl)  haoxiang_yuan@sfu.ca 

Bofan Yu  bofany@sfu.ca 

Guanming Liu (Nicholas) gla56@sfu.ca 

Zihang Yuan zya91@sfu.ca 

Anh Vu  ava48@sfu.ca 

