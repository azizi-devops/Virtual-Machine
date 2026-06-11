package de.pltlab.teaching.vms.bytecodemachine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Incomplete demo implementation of an interpreter following the decode-dispatch-loop
 * strategy.
 */
public class DecodeDispatchInterpreterProvided extends Interpreter {

	int pc;
	
	List<Object> locals = new ArrayList<>();
	Deque<Object> stack = new ArrayDeque<>();
	private Object resultValue;
	
	public DecodeDispatchInterpreterProvided(File file) throws IOException {
		super(file);
	}

	@Override
	protected void interpret() {
		
		pc = 0;
		
		while(!halted()) {
			directJump();
			
			int opcode = ((int) getBytes()[pc]) & 0xff;
			extraction();
			
			switch (opcode) {
			case 0x1a -> {
				indirectJump();
				interp_iload_0();
			}
			case 0x1b -> {
				indirectJump();
				interp_iload_1();
			}
			case 0x1c -> {
				indirectJump();
				interp_iload_2();
			}
			case 0x3b -> {
				indirectJump();
				interp_istore_0();
			}
			case 0x3c -> {
				indirectJump();
				interp_istore_1();
			}
			case 0x3d -> {
				indirectJump();
				interp_istore_2();
			}
			case 0x03 -> {
				indirectJump();
				interp_iconst_0();
			}
			case 0x04 -> {
				indirectJump();
				interp_iconst_1();
			}
			case 0xa7 -> {
				indirectJump();
				interp_goto();
			}
			case 0x68 -> {
				indirectJump();
				interp_imul();
			}
			case 0x84 -> {
				indirectJump();
				interp_iinc();
			}
			case 0x9a -> {
				indirectJump();
				interp_ifne();
			}
			case 0xb1 -> {
				indirectJump();
				interp_return();
			}
			case 0xa0 -> {
				indirectJump();
				interp_ifcmpne();
			}
			case 0xa4 -> {
				indirectJump();
				interp_ifcmple();
			}
			case 0x64 -> {
				indirectJump();
				interp_isub();
			}
			case 0x10 -> {
				indirectJump();
				interp_bipush();
			}

			// TODO add further cases
			// for factorial
			case 0xa3 -> {
				indirectJump();
				interp_ifcmpgt();
			}

		// for prime/////////////////////////////////////////////////////////

			// value1 & value2((n & 1))
			case 0x7e -> {
				indirectJump();
				interp_iand();
			}

			//	push integer constant 3 onto the stack(i = 3)
			case 0x06 -> {
				indirectJump();
				interp_iconst_3();
			}

			// if (a == b)((n & 1) == 0))
			case 0x9f -> {
				indirectJump();
				interp_ifcmpeq();
			}

			// remainder = value1 % value2;(n % i)

			case 0x70 -> {
				indirectJump();
				interp_irem();
			}

			// IRETURN is used when a method returns an integer.(return true;)

			case 0xac -> {
				indirectJump();
				interp_ireturn();
			}

			default -> throw new IllegalArgumentException("Unsupported opcode: " + opcode);
			}
			directJump();
		}
	}

	private void interp_return() {
		halt();
		pc += 1;
		indirectJump();
	}

	private void interp_iload_0() {
		stack.push(getVariableValue(0));
		pc += 1;
		indirectJump();
	}

	private void interp_iload_1() {
		stack.push(getVariableValue(1));
		pc += 1;
		indirectJump();
	}

	private void interp_iload_2() {
		stack.push(getVariableValue(2));
		pc += 1;
		indirectJump();
	}

	private void interp_istore_0() {
		setLocalVariable(0, stack.pop());
		pc += 1;
		indirectJump();
	}

	private void interp_istore_1() {
		setLocalVariable(1, stack.pop());
		pc += 1;
		indirectJump();
	}

	private void interp_istore_2() {
		setLocalVariable(2, stack.pop());
		pc += 1;
		indirectJump();
	}

	private void interp_iconst_0() {
		stack.push(Integer.valueOf(0));
		pc += 1;
		indirectJump();
	}

	private void interp_iconst_1() {
		stack.push(Integer.valueOf(1));
		pc += 1;
		indirectJump();
	}

	private void interp_bipush() {
		stack.push(Integer.valueOf(getBytes()[pc + 1]));
		pc += 2;
		indirectJump();
	}

	private void interp_goto() {
		int branchbyte1 = ((int) getBytes()[pc + 1]) & 0xff;
		int branchbyte2 = ((int) getBytes()[pc + 2]) & 0xff;
		short offset = (short) ((branchbyte1 << 8) + branchbyte2);

		pc = pc + offset;
		indirectJump();
	}

	private void interp_imul() {
		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();
		stack.push(value1 * value2);
		pc += 1;
		indirectJump();
	}

	private void interp_isub() {
		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();
		stack.push(value1 - value2);
		pc += 1;
		indirectJump();
	}

	private void interp_iinc() {
		int index = ((int) getBytes()[pc + 1]) & 0xff;
		int constValue = ((int) getBytes()[pc + 2]) & 0xff;

		locals.set(index,  ((Integer) locals.get(index)) + constValue);
		
		pc += 3;
		indirectJump();
	}

	private void interp_ifne() {
		int branchbyte1 = ((int) getBytes()[pc + 1]) & 0xff;
		int branchbyte2 = ((int) getBytes()[pc + 2]) & 0xff;
		short offset = (short) ((branchbyte1 << 8) + branchbyte2);

		int value = (Integer) stack.pop();
		if (value != 0) {
			pc = pc + offset;
		} else {
			pc += 3;
		}
		indirectJump();
	}

	private void interp_ifcmpne() {
		int branchbyte1 = ((int) getBytes()[pc + 1]) & 0xff;
		int branchbyte2 = ((int) getBytes()[pc + 2]) & 0xff;
		short offset = (short) ((branchbyte1 << 8) + branchbyte2);

		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();
		if (value1 != value2) {
			pc = pc + offset;
		} else {
			pc += 3;
		}
		indirectJump();
	}
	
	private void interp_ifcmple() {
		int branchbyte1 = ((int) getBytes()[pc + 1]) & 0xff;
		int branchbyte2 = ((int) getBytes()[pc + 2]) & 0xff;
		short offset = (short) ((branchbyte1 << 8) + branchbyte2);

		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();
		if (value1 <= value2) {
			pc = pc + offset;
		} else {
			pc += 3;
		}
		indirectJump();
	}
	
	// TODO add more routines
    // for factorial///////////////////////////////////////////////////////////
	private void interp_ifcmpgt() {
		int branchbyte1 = ((int) getBytes()[pc + 1]) & 0xff;
		int branchbyte2 = ((int) getBytes()[pc + 2]) & 0xff;
		short offset = (short) ((branchbyte1 << 8) + branchbyte2);

		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();

		if (value1 > value2) {
			pc = pc + offset;
		} else {
			pc += 3;
		}

		indirectJump();
	}


	// for prime/////////////////////////////////////////////////////////////////////////

	private void interp_iand() {
		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();

		stack.push(value1 & value2);

		pc += 1;
		indirectJump();
	}

	private void interp_iconst_3() {
		stack.push(Integer.valueOf(3));
		pc += 1;
		indirectJump();
	}


	private void interp_ifcmpeq() {
		int branchbyte1 = ((int) getBytes()[pc + 1]) & 0xff;
		int branchbyte2 = ((int) getBytes()[pc + 2]) & 0xff;
		short offset = (short) ((branchbyte1 << 8) + branchbyte2);

		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();

		if (value1 == value2) {
			pc = pc + offset;
		} else {
			pc += 3;
		}

		indirectJump();
	}


	private void interp_irem() {
		int value2 = (Integer) stack.pop();
		int value1 = (Integer) stack.pop();

		stack.push(value1 % value2);

		pc += 1;
		indirectJump();
	}


	private void interp_ireturn() {
		 resultValue = stack.pop();

		// store result if framework supports it
		// e.g. resultValue = result;

		halt();
		pc += 1;
		indirectJump();
	}




	@Override
	protected Object getVariableValue(int index) {
		if (index >= locals.size()) {
			return null;
		}
		return locals.get(index);
	}

	@Override
	public void setLocalVariable(int index, int value) {
		if (locals.size() < index + 1) {
			locals.addAll(Collections.nCopies(index - locals.size() + 1, null));
		}
		locals.set(index, value);
	}

	@Override
	public void setLocalVariable(int index, long value) {
		if (locals.size() < index + 1) {
			locals.addAll(Collections.nCopies(index - locals.size() + 1, null));
		}
		locals.set(index, value);
	}

	@Override
	public void setLocalVariable(int index, float value) {
		if (locals.size() < index + 1) {
			locals.addAll(Collections.nCopies(index - locals.size() + 1, null));
		}
		locals.set(index, value);
	}

	@Override
	public void setLocalVariable(int index, double value) {
		if (locals.size() < index + 1) {
			locals.addAll(Collections.nCopies(index - locals.size() + 1, null));
		}
		locals.set(index, value);
	}

	@Override
	public void setLocalVariable(int index, Object value) {
		if (locals.size() < index + 1) {
			locals.addAll(Collections.nCopies(index - locals.size() + 1, null));
		}
		locals.set(index, value);
	}

	@Override
	public Object getResultValue() {
		// TODO update implementation when additional return instructions are supported
		return resultValue;
	}

}
