/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape;

/**
 * The interface for a client to use to get messages from the game manager by registering
 * an observer. Any implementation of this must override the equals() method.
 * 
 * @version Apr 23, 2020
 */
public interface GameObserver {
	/**
	 * Receive a message from the game
	 * 
	 * @param message
	 */
	void notify(String message);

	/**
	 * Receive a message with the cause
	 * 
	 * @param message
	 * @param cause
	 */
	void notify(String message, Throwable cause);
}
