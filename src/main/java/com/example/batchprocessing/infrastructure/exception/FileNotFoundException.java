package com.example.batchprocessing.infrastructure.exception;

public class FileNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1899511048376447654L;

	public FileNotFoundException(String message) {

        super(message);

    }

}
