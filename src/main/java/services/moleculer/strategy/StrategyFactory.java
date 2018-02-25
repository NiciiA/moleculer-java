/**
 * MOLECULER MICROSERVICES FRAMEWORK<br>
 * <br>
 * This project is based on the idea of Moleculer Microservices
 * Framework for NodeJS (https://moleculer.services). Special thanks to
 * the Moleculer's project owner (https://github.com/icebob) for the
 * consultations.<br>
 * <br>
 * THIS SOFTWARE IS LICENSED UNDER MIT LICENSE.<br>
 * <br>
 * Copyright 2017 Andras Berkes [andras.berkes@programmer.net]<br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:<br>
 * <br>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.<br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package services.moleculer.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.moleculer.ServiceBroker;
import services.moleculer.config.MoleculerComponent;
import services.moleculer.service.Endpoint;
import services.moleculer.service.Name;

/**
 * Base class of Invocation Strategies. An Invocation Strategy is a
 * high-performance endpoint selector (Round-Robin, Random, etc.) Performance of
 * implementations:<br>
 * <br>
 * Duration of 10 000 000 loops (lower value is the better):
 * <ul>
 * <li>RoundRobinStrategyFactory: 190 msec
 * <li>XORShiftRandomStrategyFactory: 210 msec
 * <li>NanoSecRandomStrategyFactory: 240 msec
 * <li>SecureRandomStrategyFactory: 1061 msec
 * </ul>
 *
 * @see RoundRobinStrategyFactory
 * @see NanoSecRandomStrategyFactory
 * @see SecureRandomStrategyFactory
 * @see XORShiftRandomStrategyFactory
 * @see CpuUsageStrategyFactory
 */
@Name("Strategy Factory")
public abstract class StrategyFactory implements MoleculerComponent {

	// --- LOGGER ---

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	// --- START STRATEGY FACTORY ---

	/**
	 * Initializes Invocation Strategy Factory instance.
	 * 
	 * @param broker
	 *            parent ServiceBroker
	 * @param config
	 *            optional configuration of the current component
	 */
	@Override
	public void start(ServiceBroker broker) throws Exception {
	}

	// --- STOP STRATEGY FACTORY ---

	@Override
	public void stop() {
	}

	// --- FACTORY METHOD ---

	public abstract <T extends Endpoint> Strategy<T> create();

}