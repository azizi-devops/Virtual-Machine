package de.pltlab.teaching.vms.bytecodemachine.internal;

import java.util.Map;

import org.objectweb.asm.Type;

public class ByteArrayContainer {
	
	byte[] array;
	String disassembled;
	Map<Integer, Variable> variables;
	Type[] parameters;

}
