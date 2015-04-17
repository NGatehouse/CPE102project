import point
import actions

BLOB_RATE_SCALE = 4  #4
BLOB_ANIMATION_RATE_SCALE = 50 #50
BLOB_ANIMATION_MIN = 1 # 1
BLOB_ANIMATION_MAX = 3

ORE_CORRUPT_MIN = 20000
ORE_CORRUPT_MAX = 30000

QUAKE_STEPS = 10
QUAKE_DURATION = 1100
QUAKE_ANIMATION_RATE = 100

VEIN_SPAWN_DELAY = 500
VEIN_RATE_MIN = 8000
VEIN_RATE_MAX = 17000


class Background: 
   def __init__(self, name, imgs):
      self.name = name
      self.imgs = imgs
      self.current_img = 0
      
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)      
   def get_name(self):
      return self.name
      
class Actor:
   def __init__(self, name, position, rate, imgs):      
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0       
      self.pending_actions = [] 
      
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
      
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)      
   def get_rate(self):
      return self.rate   
    
   def get_name(self):
      return self.name      
   
   def remove_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.remove(action)
   def add_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.append(action)
   def get_pending_actions(self):
      if hasattr(self, "pending_actions"):
         return self.pending_actions
      else:
         return []
   def clear_pending_actions(self):
      if hasattr(self, "pending_actions"):
         self.pending_actions = []           

      
class Mining(Actor):
   def __init__(self, name,resource_limit, position, rate, imgs):
      Actor.__init__(self,name, position, rate, imgs)      
      self.resource_limit = resource_limit
      self.resource_count=0   
      
      
   def set_resource_count(self, n):
      self.resource_count = n
   def get_resource_count(self):   
      return self.resource_count      
   def get_resource_limit(self):
      return self.resource_limit

class Miner(Mining):
   def __init__(self, name,resource_limit, position, rate, imgs,animation_rate):
      Mining.__init__(self,name,resource_limit, position, rate, imgs)
      self.animation_rate = animation_rate       
   
   def get_animation_rate(self):
      return self.animation_rate   
   def create_miner_action(self,world,image_store): 
      return self.create_miner_movement(world, image_store)      
   def try_transform_miner(self,world,transform):
      new_entity = transform(world)      
      if self != new_entity: 
         world.clear_pending_actions(self)
         world.remove_entity_at(self.get_position( ))
         world.add_entity( new_entity)
         new_entity.schedule_animation(world)
      return new_entity   
   def create_animation_action(self,world,repeat_count): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         self.next_image()
         if repeat_count != 1:
            self.schedule_action(world,self.create_animation_action(world,max(repeat_count - 1, 0)),current_ticks + self.get_animation_rate()) # world.create or self.create
         return [self.get_position()]
      return action   
   def create_entity_death_action(self,world): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         pt = self.get_position()
         world.remove_entity(self)
         return [pt]
      return action
   def schedule_action(self,world,action, time):
      self.add_pending_action(action)
      world.schedule_action(action, time)
   def schedule_animation(self,world,repeat_count=0):
      self.schedule_action(world,self.create_animation_action(world,repeat_count),self.get_animation_rate())   
   def entity_string(self): # originally only in minernotfull but both are "miners"
      return ' '.join(['miner', self.name, str(self.position.x),str(self.position.y), str(self.resource_limit),str(self.rate), str(self.animation_rate)])                  

class MinerNotFull(Miner):
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      Miner.__init__(self,name, resource_limit, position, rate, imgs, animation_rate) 
      self.pending_actions = []
      self.current_img = 0     
   def try_transform(self,world): # not full into full
      if self.resource_count < self.resource_limit:
         return self
      else:
         new_entity = MinerFull(
         self.get_name(), self.get_resource_limit(),
         self.get_position(), self.get_rate(),
         self.get_images(), self.get_animation_rate())
      return new_entity      
   def _to_other(self,world, ore): # to ore
      entity_pt = self.get_position()
      if not ore:
         return ([entity_pt], False)
      ore_pt = ore.get_position()
      if actions.adjacent(entity_pt, ore_pt):
         self.set_resource_count(1 + self.get_resource_count())
         world.remove_entity(ore) 
         return ([ore_pt], True)
      else:
         new_pt = world.next_position(entity_pt, ore_pt)
         return (world.move_entity(self, new_pt), False)     
   def create_miner_movement(self,world, i_store): #  not full action
      def action(current_ticks):
         self.remove_pending_action(action)    
         entity_pt = self.get_position()
         ore = world.find_nearest(entity_pt, Ore)
         (tiles, found) = self._to_other(world, ore) # implement
         new_entity = self
         if found:
            new_entity = new_entity.try_transform_miner( world,new_entity.try_transform) # implement
         new_entity.schedule_action( world,new_entity.create_miner_action(world, i_store),current_ticks + new_entity.get_rate()) # implement
         return tiles
      return action   
   
class MinerFull(Miner):
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      Miner.__init__(self,name, resource_limit, position, rate, imgs,
      animation_rate) 
      self.pending_actions = []
      self.current_img = 0      
   def try_transform(self,world): #full to not full
      new_entity = MinerNotFull(
      self.get_name(), self.get_resource_limit(),
      self.get_position(), self.get_rate(),
      self.get_images(), self.get_animation_rate())
      return new_entity      
   def create_miner_movement(self,world,i_store):
      def action(current_ticks):
         self.remove_pending_action( action)
         entity_pt = self.get_position()
         smith = world.find_nearest(entity_pt, Blacksmith)
         (tiles, found) = self._to_other(world,smith)
         new_entity = self
         if found:
            new_entity = self.try_transform_miner(world,self.try_transform)
         new_entity.schedule_action(world,new_entity.create_miner_action(world, i_store),current_ticks + new_entity.get_rate()) #
         return tiles
      return action      
   def _to_other(self, world, smith): # to smith
      entity_pt = self.get_position()
      if not smith:
         return ([entity_pt], False)
      smith_pt = smith.get_position()
      if actions.adjacent(entity_pt, smith_pt): 
         smith.set_resource_count(smith.get_resource_count() + self.get_resource_count()) 
         self.set_resource_count(0)
         return ([], True)
      else:
         new_pt = world.next_position(entity_pt, smith_pt)
         return (world.move_entity(self, new_pt), False)
         
class Blacksmith(Mining):
   def __init__(self, name, position, imgs,resource_limit, rate,
      resource_distance=1):
      Mining.__init__(self,name,resource_limit, position, rate, imgs)    
      
      self.resource_distance = resource_distance
            
   def get_resource_distance(self):
      return self.resource_distance
   def schedule_action(self,world,action, time):
      self.add_pending_action(action)
      world.schedule_action(action, time)      
   def entity_string(self):
      return ' '.join(['blacksmith', self.name, str(self.position.x),str(self.position.y), str(self.resource_limit),str(self.rate), str(self.resource_distance)])     
   
class Vein(Actor):
   def __init__(self, name, rate, position,imgs,resource_distance=1):
      Actor.__init__(self, name, position, rate, imgs)
      self.resource_distance = resource_distance        
  
   def create_entity_death_action(self,world): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         pt = self.get_position()
         world.remove_entity(self)
         return [pt]
      return action
   def schedule_action(self,world,action, time):
      self.add_pending_action(action)
      world.schedule_action(action, time)      
   def create_vein_action(self,world,i_store):
      def action(current_ticks):
         self.remove_pending_action(action)

         open_pt = world.find_open_around(self.get_position(),self.get_resource_distance())
         if open_pt:
            ore = actions.create_ore(world,"ore - " + self.get_name() + " - " + str(current_ticks),open_pt, current_ticks, i_store)
            world.add_entity(ore)
            tiles = [open_pt]
         else:
            tiles = []

         self.schedule_action( world,
            self.create_vein_action(world, i_store),
            current_ticks + self.get_rate()) # world. or self.
         return tiles
      return action      
   def entity_string(self):
      return ' '.join(['vein', self.name, str(self.position.x),str(self.position.y), str(self.rate),str(self.resource_distance)])
   def get_resource_distance(self):
      return self.resource_distance
      
   
         
class Ore(Actor):
   def __init__(self, name,position,imgs,rate = 5000):
      Actor.__init__(self, name, position, rate, imgs)
       
   
     
   def create_entity_death_action(self,world): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         pt = self.get_position()
         world.remove_entity(self)
         return [pt]
      return action
   def schedule_action(self,world,action, time):
      self.add_pending_action(action)
      world.schedule_action(action, time)
   
   
   def create_ore_transform_action(self,world,i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         blob = actions.create_blob(world,self.get_name() + " -- blob",
         self.get_position(),
         self.get_rate() // BLOB_RATE_SCALE,
         current_ticks, i_store) 

         world.remove_entity(self)
         world.add_entity(blob)

         return [blob.get_position()]
      return action
      
   def entity_string(self):   
      return ' '.join(['ore', self.name, str(self.position.x), str(self.position.y), str(self.rate)])    
   
      
         

      
class Obstacle:
   def __init__(self, name, position, imgs):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
    
   def entity_string(self): # dont need is instance just return...
      return ' '.join(['obstacle', self.name, str(self.position.x), str(self.position.y)])
           
      
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
      
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   
   def get_name(self):
      return self.name
class OreBlob:
   def __init__(self, name, position, rate, imgs, animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []
      
   def create_animation_action(self,world,repeat_count): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         self.next_image()
         if repeat_count != 1:
            self.schedule_action(world,self.create_animation_action(world,max(repeat_count - 1, 0)),current_ticks + self.get_animation_rate()) # world.create or self.create
         return [self.get_position()]
      return action   
   def create_entity_death_action(self,world): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         pt = self.get_position()
         world.remove_entity(self)
         return [pt]
      return action
   def schedule_action(self,world,action, time):
      self.add_pending_action(action)
      world.schedule_action(action, time)
   def schedule_animation(self,world,repeat_count=0):
      self.schedule_action(world,self.create_animation_action(world,repeat_count),self.get_animation_rate())
      
   def blob_to_vein(self,world,vein):
      entity_pt = self.get_position()
      if not vein:
         return ([entity_pt], False)
      vein_pt = vein.get_position()
      if actions.adjacent(entity_pt, vein_pt):
         world.remove_entity(vein)
         return ([vein_pt], True)
      else:
         new_pt = world.blob_next_position(entity_pt, vein_pt) 
         old_entity = world.get_tile_occupant(new_pt)
         if isinstance(old_entity, Ore): # do i need to change isinstance?
            world.remove_entity(old_entity)
         return (world.move_entity(self, new_pt), False)
   def create_ore_blob_action(self,world, i_store):
      def action(current_ticks):
         self.remove_pending_action( action)
   
         entity_pt = self.get_position()
         vein = world.find_nearest(entity_pt, Vein)
         (tiles, found) = self.blob_to_vein(world,vein)

         next_time = current_ticks + self.get_rate()
         if found:
            quake = actions.create_quake(world, tiles[0], current_ticks, i_store)
            world.add_entity( quake)
            next_time = current_ticks + self.get_rate() * 2

         self.schedule_action(world,self.create_ore_blob_action(world,i_store),next_time)
            

         return tiles
      return action
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
      
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   
   def get_rate(self):
      return self.rate
   def get_name(self):
      return self.name 

   def get_animation_rate(self):
      return self.animation_rate
   def remove_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.remove(action)
   def add_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.append(action)
   def get_pending_actions(self):
      if hasattr(self, "pending_actions"):
         return self.pending_actions
      else:
         return []
   def clear_pending_actions(self):
      if hasattr(self, "pending_actions"):
         self.pending_actions = []
         
class Quake:
   def __init__(self, name, position, imgs, animation_rate):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []
      
   def create_animation_action(self,world,repeat_count): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         self.next_image()
         if repeat_count != 1:
            self.schedule_action(world,self.create_animation_action(world,max(repeat_count - 1, 0)),current_ticks + self.get_animation_rate()) # world.create or self.create
         return [self.get_position()]
      return action   
   def create_entity_death_action(self,world): # do i have to put this into every class?
      def action(current_ticks):
         self.remove_pending_action(action)
         pt = self.get_position()
         world.remove_entity(self)
         return [pt]
      return action
   def schedule_action(self,world,action, time):
      self.add_pending_action(action)
      world.schedule_action(action, time)
   def schedule_animation(self,world,repeat_count=0):
      self.schedule_action(world,self.create_animation_action(world,repeat_count),self.get_animation_rate())
   
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
      
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   
   def get_name(self):
      return self.name
   
   def get_animation_rate(self):
      return self.animation_rate
   def remove_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.remove(action)
   def add_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.append(action)
   def get_pending_actions(self):
      if hasattr(self, "pending_actions"):
         return self.pending_actions
      else:
         return []
   def clear_pending_actions(self):
      if hasattr(self, "pending_actions"):
         self.pending_actions = []
         
"""def set_position(entity, point):
   entity.position = point
def get_position(entity):
   return entity.position
def get_images(entity):
   return entity.imgs
def get_image(entity):
   return entity.imgs[entity.current_img]
def get_rate(entity):
   return entity.rate
def set_resource_count(entity, n):
   entity.resource_count = n
def get_resource_count(entity):
   return entity.resource_count
def get_resource_limit(entity):
   return entity.resource_limit
def get_resource_distance(entity):
   return entity.resource_distance
def get_name(entity):
   return entity.name
def get_animation_rate(entity):
   return entity.animation_rate
def remove_pending_action(entity, action):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions.remove(action)
def add_pending_action(entity, action):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions.append(action)
def get_pending_actions(entity):
   if hasattr(entity, "pending_actions"):
      return entity.pending_actions
   else:
      return []
def clear_pending_actions(entity):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions = []
def next_image(entity):
   entity.current_img = (entity.current_img + 1) % len(entity.imgs)
# This is a less than pleasant file format, but structured based on
# material covered in course.  Something like JSON would be a
# significant improvement.
def entity_string(entity):
   if isinstance(entity, MinerNotFull):
      return ' '.join(['miner', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.animation_rate)])
   elif isinstance(entity, Vein):
      return ' '.join(['vein', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate),
         str(entity.resource_distance)])
   elif isinstance(entity, Ore):
      return ' '.join(['ore', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate)])
   elif isinstance(entity, Blacksmith):
      return ' '.join(['blacksmith', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.resource_distance)])
   elif isinstance(entity, Obstacle):
      return ' '.join(['obstacle', entity.name, str(entity.position.x),
         str(entity.position.y)])
   else:
      return 'unknown'"""

