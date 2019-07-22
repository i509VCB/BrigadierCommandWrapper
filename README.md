# BrigadierCommandWrapper
Allows use of Brigadier to create commands in bukkit

Documentation is work in progress...

What does this do? This wrapper allows you to register commands created using Brigadier CommandDispatcher to use in bukkit with the rich tab completion system provided by brigadier on the client. This includes literal arguments, range limited integers and even some minecraft dependant arguments such as EntitySelectors and Locations. Also a packet system is in place to allow sending of custom command errors (Client side Fabric mod required).

## Features

Allows for registration of commands without touching plugin.yml.
Allows for operator only commands or commands that can only be executed by a command block for example.
Multi-version as all the vanilla arguments have been abstracted.
Fancy tab completions with EntitySelectors.

A WIP Packet system to provide custom command exceptions optionally to modded players.


## TODO:

Change registration so commands can tab complete as plugin:command.

Add aliases support.

Add nicer help topic menu integration.

Add API and finish implementation for custom command errors.

Documentation.