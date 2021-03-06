/**
 * Copyright 2012-2013 University Of Southern California
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.workflowsim.planning;

import java.util.HashMap;
import java.util.Map;

import org.cloudbus.cloudsim.Log;
import org.workflowsim.CondorVM;
import org.workflowsim.Task;

/**
 * The HEFT planning algorithm.
 * 
 * @author Pedro Paulo Vezzá Campos
 * @date Aug 16, 2013
 */
public class HEFTPlanner extends BasePlanner {

	private Map<Task, Map<CondorVM, Double>> computationCosts;
	private Map<Task, Map<Task, Double>> transferCosts;

	public HEFTPlanner() {
		computationCosts = new HashMap<>();
		transferCosts = new HashMap<>();
	}

	/**
	 * The main function
	 */
	@Override
	public void run() {
		Log.printLine("HEFT planner running with " + getTaskList().size()
				+ " tasks.");

		calculateComputationCosts();
		calculateTransferCosts();
		
		// if(false)
		// return;
		//
		// for (Iterator it = getTaskList().iterator(); it.hasNext();) {
		// Task task = (Task) it.next();
		// double duration = task.getCloudletLength() / 1000;
		//
		// for(int i = 0; i < task.getParentList().size(); i++ ){
		// Task parent = task.getParentList().get(i);
		// }
		//
		//
		// for(int i = 0; i < task.getChildList().size(); i++ ){
		// Task child = task.getChildList().get(i);
		// }
		//
		// int vmNum = getVmList().size();
		// /**
		// * Randomly choose a vm
		// */
		// Random random = new Random((long)duration);
		// int vmId = random.nextInt(vmNum);
		//
		// CondorVM vm = (CondorVM) getVmList().get(vmId);
		// //This shows the cpu capability of a vm
		// double mips = vm.getMips();
		//
		// task.setVmId(vm.getId());
		//
		//
		//
		// }
		// }

	}

	private void calculateTransferCosts() {
		for (Object taskObject1 : getTaskList()) {
			Task task1 = (Task) taskObject1;
			Map<Task, Double> taskTransferCosts = new HashMap<Task, Double>();
			
			for(Object taskObject2 : getTaskList()){
				Task task2 = (Task) taskObject2;
				taskTransferCosts.put(task2, 0.0);
			}
			
			transferCosts.put(task1, taskTransferCosts);
		}
		
		for (Object originObject : getTaskList()) {
			Task origin = (Task) originObject;
			
			
		}
	}

	private void calculateComputationCosts() {
		for (Object taskObject : getTaskList()) {
			Task task = (Task) taskObject;
			Map<CondorVM, Double> costsVm = new HashMap<CondorVM, Double>();

			for (Object vmObject : getVmList()) {
				CondorVM vm = (CondorVM) vmObject;
				if (vm.getNumberOfPes() < task.getNumberOfPes())
					costsVm.put(vm, Double.MAX_VALUE);
				else
					costsVm.put(vm,	task.getCloudletTotalLength() / vm.getMips());
			}
			computationCosts.put(task, costsVm);
		}
	}
}
