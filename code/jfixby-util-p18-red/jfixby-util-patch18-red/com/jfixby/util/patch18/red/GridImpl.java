package com.jfixby.util.patch18.red;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.Int2;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.r3.ext.api.patch18.Grid;
import com.jfixby.r3.ext.api.patch18.GridActiveArea;
import com.jfixby.r3.ext.api.patch18.GridSpecs;
import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.grid.GridBrush;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.util.patch18.red.Log.Printer;
import com.jfixby.util.patch18.red.fields.acomposed.ComposedTile;
import com.jfixby.util.patch18.red.fields.acomposed.WallsComposition;
import com.jfixby.util.patch18.red.fields.acomposed.WallsCompositionSchematic;
import com.jfixby.util.patch18.red.fields.afield.WallFunction;
import com.jfixby.util.patch18.red.fields.bool.BoolFunction;
import com.jfixby.util.patch18.red.fields.util.WallFunctionCore;
import com.jfixby.util.patch18.red.materials.JointLayer;
import com.jfixby.util.patch18.red.materials.JointLayerSchematic;

class GridImpl implements Grid {

	private final int MAX_EXPAND;
	final private P18Palette palette;
	final private GridBrushImpl brush = new GridBrushImpl(this);
	final private FabricsLevel fabric_level;

	private WallsComposition walls_composition;
	private Fabric default_fabric;

	GridActiveArea active_area;

	public GridImpl(GridSpecs field_specs) {
		this.palette = field_specs.getPalette();// !=null

		this.active_area = field_specs.getActiveGridArea();
		if (this.active_area == null) {
			this.active_area = new DefaultActiveArea();
		}

		int n = field_specs.getPalette().listFabrics().size();
		default_fabric = field_specs.getPalette().listFabrics().getFabric(0);// size>0
		brush.setDefaultFabric(default_fabric);

		fabric_level = new FabricsLevel(this);
		compose(active_area);

		MAX_EXPAND = palette.listFabrics().size() - 1;

	}

	@Override
	public P18Palette getPalette() {
		return palette;
	}

	@Override
	public GridBrush getBrush() {
		return brush;
	}

	// public FabricsLevel getFabricLevel() {
	// return this.fabric_level;
	// }

	WallsComposition process(P18Palette palette, GridActiveArea active_area) {

		// L.d("rgb", Printer.toString(argb_function));

		List<JointLayer> joint_layers = JUtils.newList();
		RelationsList materai_relationships = palette.listRelations();

		for (int i = 0; i < materai_relationships.size(); i++) {
			FabricsRelation relationship = materai_relationships.getRelation(i);
			Fabric foreground = relationship.getUpperFabric();
			Fabric background = relationship.getLowerFabric();
			// L.d(foreground + "", background + "");

			BoolFunction bool_field = new BoolFunction(fabric_level,
					foreground, background);

			WallFunction walls = WallFunctionCore.process(bool_field);

			JointLayerSchematic material_layer_schematic = new JointLayerSchematic();
			material_layer_schematic.setForegroundMaterial(foreground);
			material_layer_schematic.setBackgroundMaterial(background);
			material_layer_schematic.setWalls(walls);

			JointLayer material_layer = new JointLayer(material_layer_schematic);
			joint_layers.add(material_layer);

			// Log.d("      ", Printer.toString(boolField));

			// WallsField a_patch_field = PNGtoWalls.process(file_path,
			// true_color, false_color);
			//
			// Log.d("a_patch_field", Printer.toString(a_patch_field));

		}
		if (joint_layers.size() == 0) {
			L.e("no joint layers found");
			System.exit(0);
		}
		JointLayer first = joint_layers.getElementAt(0);

		WallsCompositionSchematic composed_walls_field_schematic = new WallsCompositionSchematic();
		// int height = first.getHeight();
		// int width = first.getWidth();
		// composed_walls_field_schematic.setHeight(height);
		// composed_walls_field_schematic.setWidth(width);
		composed_walls_field_schematic.setJointLayers(joint_layers);

		// WallsCompositionFunction walls_composition = new
		// WallsCompositionStatic(
		// composed_walls_field_schematic, materai_relationships);

		WallsComposition walls_composition = new WallsComposition(
				// composed_walls_field_schematic.getWidth(),
				// composed_walls_field_schematic.getHeight(),
				composed_walls_field_schematic.getJointLayers(),
				default_fabric, this.active_area);

		return walls_composition;

	}

	public WallsComposition getComposed() {
		return this.walls_composition;
	}

	public void compose(GridActiveArea active_area) {
		this.walls_composition = process(this.palette, active_area);
	}

	@Override
	public void print(long offset_x, long offset_y, long width, long height) {
		long grid_node_width = width + 1;
		long grid_node_height = height + 1;
		//
		L.d("grid[" + grid_node_width + "x" + grid_node_height + "]", Printer
				.toString(walls_composition, grid_node_width, grid_node_height,
						offset_x, offset_y));
	}

	public long toCellX(long grid_x) {
		return grid_x;
	}

	public long toCellY(long grid_y) {
		return grid_y;
	}

	public boolean isOutside(int cell_x, int cell_y) {
		return this.active_area.isOutside(cell_x, cell_y);
	}

	public void apply_at_node(long grid_node_x, long grid_node_y,
			Fabric current_fabric, BrushApplicationResultImpl brush_result) {
		this.set_node(grid_node_x, grid_node_y, current_fabric, brush_result);
	}

	public void apply_at_cell(long cell_x, long cell_y, int brush_size,
			Fabric current_fabric, BrushApplicationResultImpl brush_result) {

		for (int i = -brush_size; i <= brush_size + 1; i++) {
			for (int j = -brush_size; j <= brush_size + 1; j++) {
				final long grid_node_x = cell_x + i;
				final long grid_node_y = cell_y + j;
				set_node(grid_node_x, grid_node_y, current_fabric, brush_result);
				// L.d("attack", IntegerMath.newInt2(grid_node_x, grid_node_y));
			}
		}

		long origin_node_x = cell_x;
		long origin_node_y = cell_y;

		expand_walls(current_fabric, origin_node_x, origin_node_y, brush_size,
				brush_result);

		// set_node(top_left_corner_node_x, top_left_corner_node_y,
		// current_fabric, brush_result);

	}

	private void expand_walls(Fabric current_fabric, long origin_node_x,
			long origin_node_y, int brush_size,
			BrushApplicationResultImpl brush_result) {

		expand_bottom_wall(origin_node_x, origin_node_y, brush_size,
				brush_result);
		expand_right_wall(origin_node_x, origin_node_y, brush_size,
				brush_result);
		expand_left_wall(origin_node_x, origin_node_y, brush_size, brush_result);
		expand_top_wall(origin_node_x, origin_node_y, brush_size, brush_result);

	}

	private void expand_left_wall(long origin_node_x, long origin_node_y,
			int brush_size, BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			Set<Int2> ignore_elements = JUtils.newSet();
			Set<Int2> wall_expantion = JUtils.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				long left_node_x = origin_node_x - brush_size - deep;
				long top_node_y = origin_node_y - brush_size - deep;
				long bottom_node_y = origin_node_y + (brush_size + 1) + deep;

				Int2 top_corner = IntegerMath.newInt2(left_node_x, top_node_y);
				Int2 bottom_corner = IntegerMath.newInt2(left_node_x,
						bottom_node_y);

				for (long i = top_node_y; i <= bottom_node_y; i++) {
					wall_expantion.add(IntegerMath.newInt2(left_node_x, i));
				}

				// wall_expantion.print("expand-left: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = fix_vertical_wall(wall_expantion,
						brush_result, ignore_elements, top_corner,
						bottom_corner, -1);

			} while (!no_fixing_was_made && deep < MAX_EXPAND);
		}
	}

	private void expand_bottom_wall(long origin_node_x, long origin_node_y,
			int brush_size, BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			Set<Int2> ignore_elements = JUtils.newSet();
			Set<Int2> wall_expantion = JUtils.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				long right_node_x = origin_node_x + (brush_size + 1) + deep;
				long left_node_x = origin_node_x - brush_size - deep;
				long bottom_node_y = origin_node_y + (brush_size + 1) + deep;

				Int2 right_corner = IntegerMath.newInt2(right_node_x,
						bottom_node_y);
				Int2 left_corner = IntegerMath.newInt2(left_node_x,
						bottom_node_y);

				for (long i = left_node_x; i <= right_node_x; i++) {
					wall_expantion.add(IntegerMath.newInt2(i, bottom_node_y));
				}

				// wall_expantion.print("expand-bottom: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = fix_horizontal_wall(wall_expantion,
						brush_result, ignore_elements, right_corner,
						left_corner, +1);

			} while (!no_fixing_was_made && deep < MAX_EXPAND);
		}
	}

	private void expand_top_wall(long origin_node_x, long origin_node_y,
			int brush_size, BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			Set<Int2> ignore_elements = JUtils.newSet();
			Set<Int2> wall_expantion = JUtils.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				long right_node_x = origin_node_x + (brush_size + 1) + deep;
				long left_node_x = origin_node_x - brush_size - deep;
				long top_node_y = origin_node_y - brush_size - deep;

				Int2 right_corner = IntegerMath.newInt2(right_node_x,
						top_node_y);
				Int2 left_corner = IntegerMath.newInt2(left_node_x, top_node_y);

				for (long i = left_node_x; i <= right_node_x; i++) {
					wall_expantion.add(IntegerMath.newInt2(i, top_node_y));
				}

				// wall_expantion.print("expand-top: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = fix_horizontal_wall(wall_expantion,
						brush_result, ignore_elements, right_corner,
						left_corner, -1);

			} while (!no_fixing_was_made && deep < MAX_EXPAND);
		}
	}

	private void expand_right_wall(long origin_node_x, long origin_node_y,
			int brush_size, BrushApplicationResultImpl brush_result) {
		{
			int deep = 0;
			Set<Int2> ignore_elements = JUtils.newSet();
			Set<Int2> wall_expantion = JUtils.newSet();
			boolean no_fixing_was_made;
			do {
				deep++;
				wall_expantion.clear();

				long right_node_x = origin_node_x + (brush_size + 1) + deep;
				long top_node_y = origin_node_y - brush_size - deep;
				long bottom_node_y = origin_node_y + (brush_size + 1) + deep;

				Int2 top_corner = IntegerMath.newInt2(right_node_x, top_node_y);
				Int2 bottom_corner = IntegerMath.newInt2(right_node_x,
						bottom_node_y);

				for (long i = top_node_y; i <= bottom_node_y; i++) {
					wall_expantion.add(IntegerMath.newInt2(right_node_x, i));
				}

				// wall_expantion.print("expand-right: " + deep);

				wall_expantion.removeAll(ignore_elements);
				ignore_elements.clear();
				no_fixing_was_made = fix_vertical_wall(wall_expantion,
						brush_result, ignore_elements, top_corner,
						bottom_corner, +1);

			} while (!no_fixing_was_made && deep < MAX_EXPAND);
		}
	}

	private boolean fix_vertical_wall(Set<Int2> wall,
			BrushApplicationResultImpl brush_result, Set<Int2> ignore_elements,
			Int2 top_corner, Int2 bottom_corner, int direction) {

		boolean no_fixing_was_made = true;
		for (int i = 0; i < wall.size(); i++) {
			Int2 neigbour = wall.getElementAt(i);
			Int2 source = null;
			if (neigbour.equals(top_corner)) {
				source = IntegerMath.newInt2(neigbour, -direction, +1);
			} else if (neigbour.equals(bottom_corner)) {
				source = IntegerMath.newInt2(neigbour, -direction, -1);
			} else {
				source = IntegerMath.newInt2(neigbour, -direction, 0);
			}

			Fabric against_fabric = this.fabric_level.getValueAt(source.getX(),
					source.getY());
			if (against_fabric == null) {
				L.e("Wall element is bad: ", source);
				throw new Error();
			}
			if (!is_compatible(neigbour, against_fabric)) {
				fix(neigbour, against_fabric, brush_result);
				no_fixing_was_made = false;
			} else {
				ignore_elements.add(neigbour);
			}
		}
		return no_fixing_was_made;

	}

	private boolean fix_horizontal_wall(Set<Int2> wall,
			BrushApplicationResultImpl brush_result, Set<Int2> ignore_elements,
			Int2 right_corner, Int2 left_corner, int direction) {

		boolean no_fixing_was_made = true;
		for (int i = 0; i < wall.size(); i++) {
			Int2 neigbour = wall.getElementAt(i);
			Int2 source = null;
			if (neigbour.equals(right_corner)) {
				source = IntegerMath.newInt2(neigbour, -1, -direction);
			} else if (neigbour.equals(left_corner)) {
				source = IntegerMath.newInt2(neigbour, +1, -direction);
			} else {
				source = IntegerMath.newInt2(neigbour, 0, -direction);
			}
			Fabric against_fabric = this.fabric_level.getValueAt(source.getX(),
					source.getY());
			if (against_fabric == null) {
				L.e("Wall element is bad: ", source);
				throw new Error();
			}
			if (!is_compatible(neigbour, against_fabric)) {
				fix(neigbour, against_fabric, brush_result);
				no_fixing_was_made = false;
			} else {
				ignore_elements.add(neigbour);
			}
		}
		return no_fixing_was_made;

	}

	private void expand_left_wall(int origin_node_x, int origin_node_y,
			int brush_size, BrushApplicationResultImpl brush_result, int deep,
			Set<Int2> wall_expantion) {

		int left_node_x = origin_node_x - brush_size - deep;
		int top_node_y = origin_node_y - brush_size - deep;
		int bottom_node_y = origin_node_y + (brush_size + 1) + deep;

		for (int i = top_node_y + 1; i < bottom_node_y; i++) {
			wall_expantion.add(IntegerMath.newInt2(left_node_x, i));
		}

	}

	private void expand_bottom_wall(int origin_node_x, int origin_node_y,
			int brush_size, BrushApplicationResultImpl brush_result, int deep,
			Set<Int2> wall_expantion) {

		int left_node_x = origin_node_x - brush_size - deep;
		int right_node_x = origin_node_x + (brush_size + 1) + deep;
		// int top_node_y = origin_node_y - brush_size - deep;
		int bottom_node_y = origin_node_y + (brush_size + 1) + deep;

		for (int i = left_node_x + 1; i < right_node_x; i++) {
			wall_expantion.add(IntegerMath.newInt2(i, bottom_node_y));
		}

	}

	private void expand_top_wall(int origin_node_x, int origin_node_y,
			int brush_size, BrushApplicationResultImpl brush_result, int deep,
			Set<Int2> wall_expantion) {

		int left_node_x = origin_node_x - brush_size - deep;
		int right_node_x = origin_node_x + (brush_size + 1) + deep;
		int top_node_y = origin_node_y - brush_size - deep;
		// int bottom_node_y = origin_node_y + (brush_size + 1) + deep;

		for (int i = left_node_x + 1; i < right_node_x; i++) {
			wall_expantion.add(IntegerMath.newInt2(i, top_node_y));
		}

	}

	private void expand_corners(int left_node_x, int right_node_x,
			int top_node_y, int bottom_node_y,
			BrushApplicationResultImpl brush_result) {

		Int2 top_left = IntegerMath.newInt2(left_node_x, top_node_y);
		Int2 top_right = IntegerMath.newInt2(right_node_x, top_node_y);

		Int2 bottom_left = IntegerMath.newInt2(left_node_x, bottom_node_y);
		Int2 bottom_right = IntegerMath.newInt2(right_node_x, bottom_node_y);

		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = expand_corner(how_deep, top_right, brush_result,
						1, -1);
				how_deep++;

			} while (processing && how_deep <= MAX_EXPAND);
		}
		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = expand_corner(how_deep, bottom_right,
						brush_result, 1, 1);
				how_deep++;
			} while (processing && how_deep <= MAX_EXPAND);
		}
		// -------------------------
		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = expand_corner(how_deep, top_left, brush_result,
						-1, -1);
				how_deep++;

			} while (processing && how_deep <= MAX_EXPAND);
		}
		{
			int how_deep = 1;
			boolean processing = true;
			do {
				processing = expand_corner(how_deep, bottom_left, brush_result,
						-1, 1);
				how_deep++;
			} while (processing && how_deep <= MAX_EXPAND);
		}
		// JUtils.exit();
	}

	private boolean expand_corner(int how_deep, Int2 source,
			BrushApplicationResultImpl brush_result, int direction_x,
			int direction_y) {

		Int2 neigbour = IntegerMath.newInt2(source.getX() + direction_x
				* how_deep, source.getY() + direction_y * how_deep);
		L.d("expanding corner", neigbour);
		Fabric against_fabric = this.fabric_level.getValueAt(source.getX(),
				source.getY());
		if (against_fabric == null) {
			L.e("Wall element is bad: ", source);
			throw new Error();
		}
		if (!is_compatible(neigbour, against_fabric)) {
			fix(neigbour, against_fabric, brush_result);
			return true;
		}
		return false;
	}

	private void set_node(long grid_node_x, long grid_node_y,
			Fabric current_fabric, BrushApplicationResultImpl brush_result) {

		final boolean changed = this.fabric_level.set(grid_node_x, grid_node_y,
				current_fabric);

		brush_result.markAffected(grid_node_x - 1, grid_node_y - 1);
		brush_result.markAffected(grid_node_x - 0, grid_node_y - 1);
		brush_result.markAffected(grid_node_x - 1, grid_node_y - 0);
		brush_result.markAffected(grid_node_x - 0, grid_node_y - 0);

		if (changed) {
			brush_result.markChanged(grid_node_x - 1, grid_node_y - 1);
			brush_result.markChanged(grid_node_x - 0, grid_node_y - 1);
			brush_result.markChanged(grid_node_x - 1, grid_node_y - 0);
			brush_result.markChanged(grid_node_x - 0, grid_node_y - 0);

		}
	}

	public void draw(int grid_node_x, int grid_node_y, int brush_size,
			Fabric current_fabric, BrushApplicationResultImpl brush_result,
			boolean leak) {
		int deep = 0;
		Set<Int2> directly_painted_nodes = JUtils.newSet();
		Set<Int2> border_nodes = JUtils.newSet();
		Set<Int2> good_nodes = JUtils.newSet();

		for (int i = -brush_size - 1; i <= brush_size + 1; i++) {
			for (int j = -brush_size - 1; j <= brush_size + 1; j++) {
				final Int2 point = IntegerMath.newInt2(grid_node_x + i,
						grid_node_y + j);
				if (i >= -brush_size && i <= brush_size && j >= -brush_size
						&& j <= brush_size) {
					directly_painted_nodes.add(point);

				} else {
					border_nodes.add(point);
				}

			}
		}
		if (!leak) {
			return;
		}
		good_nodes.addAll(directly_painted_nodes);

		check_and_fix(current_fabric, good_nodes, border_nodes, brush_result,
				deep);

	}

	private void check_and_fix(Fabric current_fabric, Set<Int2> good_nodes,
			Set<Int2> border_nodes, BrushApplicationResultImpl brush_result,
			int deep) {
		if (deep >= 4) {
			return;
		}

		Set<Int2> to_check_n_fix = JUtils.newSet();
		to_check_n_fix.addAll(border_nodes);

		Set<Int2> checked_ok = JUtils.newSet();

		for (int i = 0; i < to_check_n_fix.size(); i++) {
			Int2 element_to_check = to_check_n_fix.getElementAt(i);
			if (is_compatible(element_to_check, current_fabric)) {
				checked_ok.add(element_to_check);
			}
		}
		// good_nodes.addAll(checked_ok);
		to_check_n_fix.removeAll(checked_ok);

		Map<Int2, Set<Int2>> suburbs = JUtils.newMap();

		for (int i = 0; i < to_check_n_fix.size(); i++) {
			Set<Int2> next_border_nodes = JUtils.newSet();
			Int2 element_to_fix = to_check_n_fix.getElementAt(i);
			fix(element_to_fix, current_fabric, brush_result);
			// good_nodes.add(element_to_fix);

			collectNeighbours(element_to_fix, next_border_nodes);
			next_border_nodes.removeAll(good_nodes);
			suburbs.put(element_to_fix, next_border_nodes);
			// deep++;
			// check_and_fix(current_fabric, good_nodes, next_border_nodes,
			// brush_result, deep);
			// deep--;
		}

		for (int k = 0; k < suburbs.size(); k++) {
			Int2 origin = suburbs.getKeyAt(k);
			Set<Int2> his_border_nodes = suburbs.getValueAt(k);
			Fabric next_fabric = this.fabric_level.getValueAt(origin.getX(),
					origin.getY());
			deep++;
			check_and_fix(next_fabric, good_nodes, his_border_nodes,
					brush_result, deep);
			deep--;
		}

		// suburbs.print("suburbs");
	}

	private void collectNeighbours(Int2 origin, Set<Int2> next_border_nodes) {
		final long grid_node_x = origin.getX();
		final long grid_node_y = origin.getY();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= +1; j++) {
				final Int2 point = IntegerMath.newInt2(grid_node_x + i,
						grid_node_y + j);
				if (i == 0 && j == 0) {
				} else {
					next_border_nodes.add(point);
				}

			}
		}

	}

	private boolean is_compatible(Int2 position_to_check, Fabric origin_value) {
		Fabric neighbour_value = this.fabric_level.getValueAt(
				position_to_check.getX(), position_to_check.getY());

		if (neighbour_value == null) {
			return false;
		}

		if (compatible(neighbour_value, origin_value)) {
			return true;
		}

		return false;
	}

	private Fabric fix(Int2 position_to_check, Fabric origin_value,
			BrushApplicationResultImpl brush_result) {
		Fabric neighbour_value = this.fabric_level.getValueAt(
				position_to_check.getX(), position_to_check.getY());

		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
		}

		final Fabric compatible_fabric = findCompatibleFabric(origin_value,
				neighbour_value);

		final long grid_node_x = position_to_check.getX();
		final long grid_node_y = position_to_check.getY();

		this.set_node(grid_node_x, grid_node_y, compatible_fabric, brush_result);

		return compatible_fabric;
	}

	private void check_and_fix(Int2 position_to_check,
			Set<Int2> directly_painted_nodes,
			BrushApplicationResultImpl brush_result, final Fabric origin_value,
			Set<Int2> good_nodes, Set<Int2> border_nodes,
			Set<Int2> second_border_nodes) {

		Fabric neighbour_value = this.fabric_level.getValueAt(
				position_to_check.getX(), position_to_check.getY());

		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
		}

		if (compatible(neighbour_value, origin_value)) {
			return;
		}

		final Fabric compatible_fabric = findCompatibleFabric(origin_value,
				neighbour_value);

		final long grid_node_x = position_to_check.getX();
		final long grid_node_y = position_to_check.getY();

		fix(grid_node_x, grid_node_y, compatible_fabric, brush_result,
				good_nodes, border_nodes, second_border_nodes);

	}

	private void fix(long grid_node_x, long grid_node_y,
			Fabric compatible_fabric, BrushApplicationResultImpl brush_result,
			Set<Int2> good_nodes, Set<Int2> border_nodes,
			Set<Int2> second_border_nodes) {

		Set<Int2> new_border_nodes = JUtils.newSet();
		for (long i = -1; i <= 1; i++) {
			for (long j = -1; j <= +1; j++) {
				final Int2 point = IntegerMath.newInt2(grid_node_x + i,
						grid_node_y + j);
				if (i == 0 && j == 0) {
					this.set_node(grid_node_x + i, grid_node_y + j,
							compatible_fabric, brush_result);
					good_nodes.add(point);
				} else {
					new_border_nodes.add(point);
				}

			}
		}
		new_border_nodes.removeAll(good_nodes);

	}

	private void check_and_fix_neighbours(Int2 position_to_check,
			List<Int2> directly_painted_nodes,
			BrushApplicationResultImpl brush_result) {

		List<Int2> neighbours = JUtils.newList();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i != 0 && j != 0) {
					Int2 position = IntegerMath.newInt2(
							position_to_check.getX() + i,
							position_to_check.getY() + j);
					if (!directly_painted_nodes.contains(position)) {
						neighbours.add(position);
					}
				}
			}
		}
		Int2 origin = directly_painted_nodes.getElementAt(0);

		for (int i = 0; i < neighbours.size(); i++) {
			Int2 neighbour = neighbours.getElementAt(i);
			check_and_fix(neighbour, origin, brush_result);
		}

	}

	private void check_and_fix(Int2 neighbour, Int2 origin,
			BrushApplicationResultImpl brush_result) {

		Fabric neighbour_value = this.fabric_level.getValueAt(neighbour.getX(),
				neighbour.getY());

		Fabric origin_value = this.fabric_level.getValueAt(origin.getX(),
				origin.getY());

		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
		}

		if (compatible(neighbour_value, origin_value)) {
			return;
		}

		Fabric compatible_fabric = findCompatibleFabric(origin_value,
				neighbour_value);

		long grid_node_x = neighbour.getX();
		long grid_node_y = neighbour.getY();

		set_node(grid_node_x, grid_node_y, compatible_fabric, brush_result);

	}

	int[] grid_cell_x = new int[4];
	int[] grid_cell_y = new int[4];

	private void checkAndFixNeighbours(int grid_node_x, int grid_node_y,
			BrushApplicationResultImpl brush_result, Set<Int2> ignore) {
		Int2 origin = IntegerMath.newInt2(grid_node_x, grid_node_y);

		List<Int2> neighbours = JUtils.newList();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i != 0 && j != 0) {
					Int2 position = IntegerMath.newInt2(grid_node_x + i,
							grid_node_y + j);
					if (!ignore.contains(position)) {
						neighbours.add(position);

					}
				}
			}
		}

		for (int i = 0; i < neighbours.size(); i++) {
			Int2 neighbour = neighbours.getElementAt(i);
			ignore.add(neighbour);
			check_and_fix(neighbour, ignore, origin, brush_result);
		}
	}

	private void check_and_fix(Int2 neighbour, Set<Int2> ignore, Int2 origin,
			BrushApplicationResultImpl brush_result) {

		Fabric neighbour_value = this.fabric_level.getValueAt(neighbour.getX(),
				neighbour.getY());

		Fabric origin_value = this.fabric_level.getValueAt(origin.getX(),
				origin.getY());
		boolean stop_here = false;
		if (neighbour_value == null) {
			neighbour_value = this.palette.listFabrics().getFabric(0);
			// return;
			stop_here = true;
		} else if (compatible(neighbour_value, origin_value)) {
			return;
		}
		Fabric current_fabric = findCompatibleFabric(origin_value,
				neighbour_value);

		long grid_node_x = neighbour.getX();
		long grid_node_y = neighbour.getY();

		// if (set_node(grid_node_x, grid_node_y, current_fabric, brush_result)
		// && !stop_here) {
		//
		// checkAndFixNeighbours(grid_node_x, grid_node_y, brush_result,
		// ignore);
		// }

	}

	private Fabric findCompatibleFabric(Fabric origin_value,
			Fabric neighbour_value) {

		// Fabric neighbour_value = this.fabric_level.getValueAt(
		// neighbour.x_index, neighbour.y_index);

		// Fabric origin_value = this.fabric_level.getValueAt(origin.x_index,
		// origin.y_index);

		Fabric next = this.palette.findClosestFabric(origin_value,
				neighbour_value);

		return next;
	}

	private boolean compatible(Fabric fabric_a, Fabric fabric_b) {
		if (fabric_a == fabric_b) {
			return true;
		}
		FabricsRelation relation = this.palette.listRelations()
				.findRelationFor(fabric_a, fabric_b);
		if (relation == null) {
			return false;
		}
		return true;
	}

	public void refresh(BrushApplicationResultImpl brush_result) {
		Collection<FabricPoint> values = this.fabric_level.function_set
				.getAllValues();
		for (int i = 0; i < values.size(); i++) {
			FabricPoint element = values.getElementAt(i);
			long cell_x = element.X;
			long cell_y = element.Y;
			brush_result.markChanged(cell_x, cell_y);
		}

	}

	public ComposedTile getValueAt(Int2 element) {
		return this.walls_composition.getValue(element.getX(), element.getY());
	}

}
