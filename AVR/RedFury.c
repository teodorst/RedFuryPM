/*
 * lab1.c
 */

#include "usart.h"
#define F_CPU 16000000
#include <avr/io.h>
#include <util/delay.h>



int main()
{
    //USART
    USART0_init();

	char command;
	PORTA = 0;
	DDRA |= (1 << PB0) | (1 << PB1) | (1 << PB2) | (1 << PB3);
	while(1) {
		command = USART0_receive();
		PORTD |= (1 << PD7);
		switch (command) {
			case 'a':
				PORTA |= (1 << PA0);
				_delay_ms(100);
				break;
			case 'b':
				PORTA &= ~(1 << PA0);
				_delay_ms(100);
				break;
			case 'c':
				PORTA |= (1 << PA1);
				_delay_ms(100);
				break;
			case 'd':
				PORTA &= ~(1 << PA1);
				_delay_ms(100);
				break;
			case 'e':
				PORTA |= (1 << PA2);
				_delay_ms(100);
				break;
			case 'f':
				PORTA &= ~(1 << PA2);
				_delay_ms(100);
				break;
			case 'g':
				PORTA |= (1 << PA3);
				_delay_ms(100);
				break;
			case 'h':
				PORTA &= ~(1 << PA3);
				_delay_ms(100);
				break;
			default:
				PORTA = 0;
				_delay_ms(100);
				break;
		}
	}

    return 0;
}
