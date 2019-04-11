# Ilex 6502
## An MOS 6502 assembler and emulator

This project began as my own effort to better understand the MOS 6502 instruction set. I figured writing an assembler would be a terrific way to grok the addressing modes and internalize the instructions.

Perhaps some day I'll get back to writing that Atari 2600 game.

## Warning - This is a work in progress

The editor window works pretty well, but seems to have some strange bugs where it duplicates code as you type it.  It seems to be a display problem, as it doesn't save this extra code to the .asm file.

The assembler works most of the time.  It will save your code in .asm files, then Assemble to .bin files.  The Run button currently doesn't run the whole program, but rather steps through it as I'm still debugging.

The Emulator Screen is just a TextArea right now.  This needs to change to something like a drawing surface that will more appropriately emulate a screen.

Any instructions that I currently have implemented will modify the appropriate registers.

The Memory button will eventually allow the user to browse the current contents of memory.

## Assembler Syntax

Comments begin with ';' (semi-colon)

Labels begin at the start of the line and end with a colon.

Instructions must be preceded by whitespace (think Python)

More to come...
