# Tinee_project 

## Description

Tinee is a simple text-based system for in a house. It is intended to be a lightweight utility that can 
be quickly integrated into other development projects. It has client code which has the structure of how 
the system is dealing with the server. To run the system, it requires a server and Tinee system to 
run at the same time. In this project, the server runs on Command Prompt, and the CPClient code runs on NetBeans. 
The system allows users to read, save and manage the tickets and push the message after managing 
the information, saving the news on the server.  It will enable the user can terminate the session at any time.
The server records a database of tickets identified by a tag. Each tine contains the username of its author and a single line test as the message body.  
The user needs to use “[Tag]” to execute the read and manage command. Exit, undo, discard, close, and push do not require any tag. They run just by writing the command. 
However, line command requires a message, but “Tine message line should be non-empty and no longer than 48 characters“. 

Whenever client code runs, it will ask Username, Hostname, if it is not added in the code and system assume user always enters correct user details. 
After that, it will display three options “[Main] Enter the command: read [mytag], manage [mytag], exit.” 
If the user enters read [tag], the system (CPClient code) executes the request to the server and access information on the given tag, displaying the saved information. 
If the user enters manage[tag], the system (CPClient code) executes the request to the server and gives other options to the user, which are “[Drafting] Enter the command: line [mytext], undo, push, discard, close, exit.” 
This allows the user to write the message using “line[message]” and then use push command to save the message on the server with a given tag. 
Undo command is used to remove the last added message in the ticket, Discard command is used to get back to the main stage, and it also removes all the message on the ticket before getting back to the main stage,
, Close command is used to close the ticket, and only the ticket creator or author of the ticket can close the ticket and exit command, which terminate the session. 


Every time system runs the stage display, where the user can read, manage, or exit. But if the user enters the manage [tag] command, then it will display an option starting with Drafting, and at this stage, 
the user can use the line, undo, push, discard, close, and exit command.
In the Drafting, stage the user cannot read or manage the different tines.




## Installation

The system require the server and client. In this installation we are setting up the server on the command prompt and client in the netbeans
1. Creating a directory for the tinee system. 
2. Open a command prompt (Assuming git hub installed on your computer)
3. Locate the created folder for the tinee system
3. Use git pull "link from the git hub" or extract the given zip files in the designated folder
4. Open net beans -> click on "file" -> New Project -> Java -> Java Project with Existing Sources
5.Select your directory as the project folder
6. In the next step, add teh existing src directory to "source package folder" then click next

## Credits
User id. 16084787
