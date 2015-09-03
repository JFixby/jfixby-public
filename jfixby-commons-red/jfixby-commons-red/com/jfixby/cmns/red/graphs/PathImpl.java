package com.jfixby.cmns.red.graphs;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.graphs.PathInGraph;
import com.jfixby.cmns.api.graphs.PathState;
import com.jfixby.cmns.api.graphs.PathStep;
import com.jfixby.cmns.api.log.L;

public class PathImpl<VertexType, EdgeType> implements PathInGraph<VertexType, EdgeType> {

	final List<StateImpl<VertexType, EdgeType>> states = JUtils.newList();
	final List<StepImpl<VertexType, EdgeType>> steps = JUtils.newList();

	@Override
	public int numberOfStates() {
		return states.size();
	}

	@Override
	public PathState<VertexType, EdgeType> getState(int state_number) {
		return this.states.getElementAt(state_number);
	}

	@Override
	public int numberOfSteps() {
		return steps.size();
	}

	@Override
	public PathStep<VertexType, EdgeType> getStep(int step_number) {
		return this.steps.getElementAt(step_number);
	}

	public void setup(List<VertexImpl<VertexType, EdgeType>> states, List<EdgeImpl<VertexType, EdgeType>> steps) {
		for (int i = 0; i < states.size(); i++) {
			VertexImpl<VertexType, EdgeType> vertex = states.getElementAt(i);
			StateImpl<VertexType, EdgeType> state = new StateImpl<VertexType, EdgeType>();
			state.setVertex(vertex);
			this.states.add(state);
		}

		for (int i = 0; i < steps.size(); i++) {
			EdgeImpl<VertexType, EdgeType> edge = steps.getElementAt(i);
			StepImpl<VertexType, EdgeType> step = new StepImpl<VertexType, EdgeType>();
			step.setEdge(edge);
			StateImpl<VertexType, EdgeType> leftState = this.states.getElementAt(i);
			StateImpl<VertexType, EdgeType> rightState = this.states.getElementAt(i + 1);

			step.setLeftState(leftState);
			step.setRightState(rightState);

			this.steps.add(step);

		}
	}

	public void print(String tag) {
		String tmp = "Path[" + tag + "] ";
		if (this.states.size() > 0) {

			tmp = tmp + " " + this.states.getElementAt(0).getVertex() + "";
		}
		for (int i = 0; i < this.steps.size(); i++) {
			StepImpl<VertexType, EdgeType> step = this.steps.getElementAt(i);
			tmp = tmp + " -[" + step.getEdge() + "]-> " + step//
					.getRightState()//
					.getVertex();
		}
		L.d(tmp);
	}

	@Override
	public List<VertexType> toVerticesList() {
		List<VertexType> vertices = JUtils.newList();
		for (int i = 0; i < this.numberOfSteps(); i++) {
			VertexType object = this.getState(i).getVertex().getVertexObject();
			vertices.add(object);
		}

		return vertices;
	}
}
