all: RedFury.hex

RedFury.elf: RedFury.c usart.c
	avr-g++ -mmcu=atmega324p -DF_CPU=16000000 -Wall -Os -o $@ $^

RedFury.hex: RedFury.elf
	avr-objcopy -j .text -j .data -O ihex RedFury.elf RedFury.hex
	avr-size RedFury.elf

clean:
	rm -rf RedFury.elf RedFury.hex

.PHONY: all clean
