package de.pltlab.teaching.vms.bytecodemachine;

import java.io.File;
import java.io.IOException;

public class DirectThreadedIntepreter extends Interpreter {
	
	// TODO add required fields

	public DirectThreadedIntepreter(File file) throws IOException {
		super(file);
		
		// TODO add implementation
	}

	@Override
	protected void interpret() {
		// TODO add implementation
	}

	// TODO add implementation

	@Override
	protected Object getVariableValue(int index) {
		// TODO implement properly
		return null;
	}

	@Override
	public void setLocalVariable(int index, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalVariable(int index, long value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalVariable(int index, float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalVariable(int index, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalVariable(int index, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getResultValue() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
