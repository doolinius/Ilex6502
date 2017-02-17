; calculate Fibonacci numbers that will
; fit in 8 bits

; two zero-page memory locations 
; this will be like a two-byte rolling frame
MemN1 = $80
MemN2 = $81

	ORG $0600 ; start on memory page 6
	LDA #1 ;start with 1, 1
	LDX #0
	;LDD   ;intentionally invalid instruction

	STA MemN1 ; n1 = 1
	STA MemN2 ; n2 = 1
Loop:
	; since the accumulator always holds
	;     n2 at this point, all we have to
	;     do is add n1, which is MemN1 plus
	;     the index register
	ADC MemN1,x ; n2 + n1 -> accumulator
	INX	     ; increment index register
	STA MemN2,x ; store new result in mem2
	BCC Loop    ; loop if less than 255
