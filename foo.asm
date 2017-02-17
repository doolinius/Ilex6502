;this is a test program
foo = $80
Begin:  
	LDX #0 ; load X register with 0
	LDA $3456
	BCC Begin

Loop:   
	TXA
	STA $84
	JMP Loop

	LDY %00000000
	JMP Next
	PHP
	STA foo

Next:
	INX
	ROL A
