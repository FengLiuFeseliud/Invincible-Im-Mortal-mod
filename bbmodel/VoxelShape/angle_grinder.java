public VoxelShape makeShape(){
	VoxelShape shape = VoxelShapes.empty();
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0.8125, 1, 0.3125, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 0.1875, 0.3125, 0.1875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0.8125, 0.1875, 0.3125, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0, 1, 0.3125, 0.1875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.3125, 0, 1, 0.4375, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.4375, 0, 1, 1, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.4375, 0, 0.125, 1, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.9375, 0, 0.875, 1, 0.9375));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.9375, 0.875, 1, 1));

	return shape;
}