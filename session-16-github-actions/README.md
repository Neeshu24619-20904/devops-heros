# Session 16 Assignment Submission:

## What was done:

We were asked to build and application and use github action to build a ci/cd pipeline for this and show the demo

***For the files and folders please visit the github repo mentioned later in this readme***

## What is in the yml file:

We have done the following in the yml file:
- It runs on ubuntu latest
- Has 2 jobs:
  - test
  - build
- Test has 5 steps:
  - Checkout the source code
  - Setup Python
  - Display the Python Version
  - Install required dependencies listed in requirements.txt
  - Run pytest
- Build has 5 steps too:
  - Checkout the source code
  - Setup Python
  - Build application
  - Show build output
  - Upload build artifact
- What artifact does is that it uploads a zip file downloadble that user can download that is already setup and build using the steps mentioned before

* Screenshots of workflow in github is shown below.

**To redirect to the repo of the cicd pipline, [click here.](https://github.com/adx19/github-actions-pipeline-practice)**

## Screenshots:

![test 1](./session-16-github-actions/10-final-cicd-pipeline/screenshots/cicd1.png)

![test 2](./session-16-github-actions/10-final-cicd-pipeline/screenshots/cicd2.png)



