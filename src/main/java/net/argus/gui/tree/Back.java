package net.argus.gui.tree;

class Back {
	/*
	protected CJSONValue value;
	protected CJSONString name;
	
	protected List<CardinalTreeNode> children = new ArrayList<CardinalTreeNode>();
	
	private ListenerManager<TreeListener> listenerManager = new ListenerManager<TreeListener>();
	
	protected CardinalTreeNode(CJSONItem item) {
		this(item.getValue(), item.getCJSONName());
	}
	
	protected CardinalTreeNode(CJSONValue obj, CJSONString name) {
		this.value = obj;
		this.name = name;
		
		nextTreeNode();
	}
	
	public CardinalTreeNode(CJSON cjson) {
		this.value = cjson.getMainObject();
		this.name = new CJSONString("main");
		
		nextTreeNode();
	}
	
	public void nextTreeNode() {
		if(value instanceof CJSONObject)
			for(int i = 0; i < value.getObject().getValue().size(); i++) {
				if(value.getObject().getValue().get(i).getValue() instanceof CJSONObject)
					children.add(new CardinalTreeNode(value.getObject().getValue().get(i)));
				else
					children.add(new CardinalTreeNode(value.getObject().getValue().get(i)));
			}
	}
	
	public TreeSelectionListener getTreeSelectionListener() {
		return new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				if(e.getNewLeadSelectionPath() != null) {
					Object[] path = e.getNewLeadSelectionPath().getPath();
					CJSONValue obj = value;
					for(int i = 1; i < path.length; i++) {
						CJSONValue ob = obj.getValue(path[i].toString());
						
						obj = ob;
						if(!(ob instanceof CJSONObject))
							break;
					}

					if(CharacterManager.isNumber(obj.toString()))
						for(TreeListener listener : listenerManager.getListeners())
							listener.selectedItemValue(Integer.valueOf(obj.toString()));
				}
			}
		};
	}
	
	public List<CardinalTreeNode> getListPath(int id, List<CardinalTreeNode> children) {
		List<CardinalTreeNode> trees = new ArrayList<CardinalTreeNode>();
		for(CardinalTreeNode tr : children!=null?children:this.children)
			if(!(tr.value.getValue() instanceof CJSONObject)) {
				if(Integer.valueOf(tr.value.getString()) == id) {
					trees.add(tr);
					return trees;
				}
			}else {
				trees = getListPath(id, tr.children);
				trees.add(0, tr);
				return trees;
			}
		return null;
	}
	
	public List<CardinalTreeNode> getListPath(String name, List<CardinalTreeNode> children) {
		List<CardinalTreeNode> trees = new ArrayList<CardinalTreeNode>();
		for(CardinalTreeNode tr : children!=null?children:this.children) {
			if(!(tr.value instanceof CJSONObject)) {
				if(/*tr.value instanceof CJSONString && *//*tr.value.getString().equals(name)) {
					System.out.println(tr);
					trees.add(tr);
					return trees;
				}
			}else {
				trees = getListPath(name, tr.children);
				if(trees != null)
					trees.add(0, tr);
				return trees;
			}
		}
		return null;
	}
	
	public Object[] getPath(int id, List<CardinalTreeNode> children) {
		for(CardinalTreeNode tr : children!=null?children:this.children)
			if(!(tr.value.getValue() instanceof CJSONObject)) {
				if(Integer.valueOf(tr.value.getString()) == id)
					return new Object[] {tr};
			}else
				return ArrayManager.add(getPath(id, tr.children), tr, 0);
		return null;
	}
	
	public Object[] getPath(String name, List<CardinalTreeNode> children) {
		for(CardinalTreeNode tr : children!=null?children:this.children)
			if(!(tr.value.getValue() instanceof CJSONObject)) {
				if(tr.name.toString().equals(name))
					return new Object[] {tr};
			}else
				return ArrayManager.add(getPath(name, tr.children), tr, 0);
		return null;
	}
	
	public CardinalTreeNode[] getTreePath(int id, List<CardinalTreeNode> children) {
		return (CardinalTreeNode[]) getListPath(id, children).toArray(new CardinalTreeNode[getListPath(id, children).size()]);
	}
	
	public CardinalTreeNode[] getTreePath(String name, List<CardinalTreeNode> children) {
		List<CardinalTreeNode> path = getListPath(name, children);
		if(path != null)
			return (CardinalTreeNode[]) path.toArray(new CardinalTreeNode[path.size()]);
		else
			return null;
	}
	
	public int getSelectedId(TreePath path) {
		if(path != null) {
			Object[] paths = path.getPath();
			Debug.println(paths);
			CardinalTreeNode[] objs = (CardinalTreeNode[]) getTreePath(paths[paths.length - 1].toString(), null);
			if(objs != null) {
				CardinalTreeNode obj = objs[objs.length - 1];
				return Integer.valueOf(obj.value.getString());
			}else
				return -1;
			
		}else
			return -1;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {return children.get(childIndex);}

	@Override
	public int getChildCount() {return children.size();}

	@Override
	public TreeNode getParent() {return null;}

	@Override
	public int getIndex(TreeNode node) {return children.indexOf(node);}

	@Override
	public boolean getAllowsChildren() {if(value instanceof CJSONObject) return true; else return false;}

	@Override
	public boolean isLeaf() {return children.size()<=0;}

	@Override
	public Enumeration<CardinalTreeNode> children() {return null;}
	
	@Override
	public String toString() {return name.getString();}
	
	public void addTreeListener(TreeListener listener) {listenerManager.addListener(listener);}*/

}
