# CarPetsMod
My following car pets mod source

This mod adds car pets to minecraft! Feel free to add a pullrequest of code you would like to see added!

Extended description with compiled binaries and images (modpage): https://www.curseforge.com/minecraft/mc-mods/car-pets

*DEVELOPERS HERE*:
MAIN CODE UNDER 1.17 branch. I'm terrible at Github...

Even if this code is very strange, if you boil down the giant array lists and storage of models, this is a working way of adding
.obj model rendering to mobs in minecraft with proper lighting and with textures/ dynamic loading of objs depending on if a mod is loaded (Say for instance, you wanted to give users the ability to do potato quality). Also the storage of lighting coords and overlay cords in the followingcarmodel class is essential to lighting the model layer obj model.

You are allowed to copy this code word for word in it's entirety for the purpose of adding obj models to your entities. then you can modify it. If your code deviates to 60% difference to what mine looks like, then you can consider not crediting me. Otherwise, give me a mention as a link to this repository. 

This code can help you solve:
texture atlas overflow / bleeding / mistakes on .obj models (use model manager in 1.18 not model registry. may still apply in the future)
lighting bugs with .obj models on entities
loading .obj models as directly as possible using forge
rendering .obj models on entities or anywhere there is a render function
positioning .obj models on entities
and maybe more!
