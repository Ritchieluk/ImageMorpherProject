# Program 3 README
___
Luke Ritchie,

Robert Wheaton,

CS335, Dr. Seales

Program 3: Phase 1

## Controller: JMorph
- Contains a MouseActionListener that can move the points on each grid
- Takes in both grids' matricies and creates a transformation between them
- Has a button handler to upload images
## View: JImageGrid
- Takes an action listener so it can add it to the models
- Contains an image.
- Contains a matrix of all of its points.
- When a dot is moved, the triangle should also move with it.
- Transform(frames)
- Made up of JTriangleTextures, has moveable dots that actively change the locations of the triangle textures.
- sets the textures of its triangles given its image
## Model: JTriangleTexture
The model is a combination of three points. It tracks its points and can redraw itself. It should default 
- Contains a set texture function
- It's points can be accessed and updated.
