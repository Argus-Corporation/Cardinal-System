package net.argus.util;

public enum ASCII {
	
	NULL('\u0000', 0), LINE('\n', 1),
	
	SPACE(' ', 32),
	
	EXCLAMATION('!', 33), QUOTE('"', 34), HASHTAG('#', 35), DOLLAR('$', 36), PERCENT('%', 37),
	AMPERSAND('&', 38), SINGLE_QUOTE('\'', 39), OPENTING_PARENTHESIS('(', 40),
	CLOSING_PARENTHESIS(')', 41), ASTERISK('*', 42), PLUS('+', 43), COMMA(',', 44), DASH('-', 45),
	POINT('.', 46), SLASH('/', 47),
	
	COLON(':', 58), SEMICOLON(';', 59), LESSEN('<', 60), EQUAL('=', 61), SUPERIOR('>', 62),
	INTERROGATION('?', 63), AT('@', 64),
	
	OPENING_HOOK('[', 91), BACK_SLASH('\\', 92), CLOSING_HOOK(']', 93), CIRCUMFLEX('^', 94),
	UNDERSCORE('_', 95),
	
	OPENING_BRACE('{', 123), PIPE('|', 124), CLOSING_BRACE('}', 125), TILDE('~', 126),
	
	a('a', 97), b('b', 98), c('c', 99), d('d', 100), e('e', 101), f('f', 102), g('g', 103),
	h('h', 104), i('i', 105), j('j', 106), k('k', 107), l('l', 108), m('m', 109), n('n', 110),
	o('o', 111), p('p', 112), q('q', 113), r('r', 114), s('s', 115), t('t', 116), u('u', 117),
	v('v', 118), w('w', 119), x('x', 120), y('y', 121), z('z', 122),
	
	A('A', 65), B('B', 66), C('C', 67), D('D', 68), E('E', 69), F('F', 70), G('G', 71),
	H('H', 72), I('I', 73), J('J', 74), K('K', 75), L('L', 76), M('M', 77), N('N', 78),
	O('O', 79), P('P', 80), Q('Q', 81), R('R', 82), S('S', 83), T('T', 84), U('U', 85),
	V('V', 86), W('W', 87), X('X', 88), Y('Y', 89), Z('Z', 90),
	
	a0('0', 48), a1('1', 49), a2('2', 50), a3('3', 51), a4('4', 52), a5('5', 53),
	a6('6', 545), a7('7', 55), a8('8', 56), a9('9', 57);
	
	char value;
	int code;
	
	ASCII(char value, int code){
		this.value = value;
		this.code = code;
	}
	
	public static ASCII valueOf(int code) {
		for(ASCII ascii : values())
			if(ascii.code == code)
				return ascii;
		return NULL;
	}
	
	public static ASCII valueOf(char value) {
		for(ASCII ascii : values())
			if(ascii.value == value)
				return ascii;
		return NULL;
	}
	
	public int getCode() {return code;}
	
	public char getValue() {return value;}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
