import entities
import pygame
import ordered_list
import actions
import occ_grid
import point

class WorldModel:
   def __init__(self, num_rows, num_cols, background):
      self.background = occ_grid.Grid(num_cols, num_rows, background)
      self.num_rows = num_rows
      self.num_cols = num_cols
      self.occupancy = occ_grid.Grid(num_cols, num_rows, None)
      self.entities = []
      self.action_queue = ordered_list.OrderedList()

         
      
   
   def remove_entity(self, entity):
      for action in entity.get_pending_actions():
         self.unschedule_action(action)
      entity.clear_pending_actions()
      self.remove_entity(entity)
      
   
      
   def find_open_around(self, pt, distance): #done
      for dy in range(-distance, distance + 1):
         for dx in range(-distance, distance + 1):
            new_pt = point.Point(pt.x + dx, pt.y + dy)

            if (self.within_bounds(new_pt) and
               (not self.is_occupied(new_pt))):
               return new_pt

      return None
   
   def blob_next_position(self, entity_pt, dest_pt):
      horiz = sign(dest_pt.x - entity_pt.x)
      new_pt = point.Point(entity_pt.x + horiz, entity_pt.y)

      if horiz == 0 or (self.is_occupied(new_pt) and
         not isinstance(self.get_tile_occupant(self, new_pt),
         entities.Ore)):
         vert = sign(dest_pt.y - entity_pt.y)
         new_pt = point.Point(entity_pt.x, entity_pt.y + vert)

         if vert == 0 or (self.is_occupied(new_pt) and
            not isinstance(self.get_tile_occupant(self, new_pt),
            entities.Ore)):
            new_pt = point.Point(entity_pt.x, entity_pt.y)

      return new_pt

   def next_position(self, entity_pt, dest_pt):
      horiz = sign(dest_pt.x - entity_pt.x)
      new_pt = point.Point(entity_pt.x + horiz, entity_pt.y)

      if horiz == 0 or self.is_occupied(new_pt):
         vert = sign(dest_pt.y - entity_pt.y)
         new_pt = point.Point(entity_pt.x, entity_pt.y + vert)
   
         if vert == 0 or self.is_occupied(new_pt):
            new_pt = point.Point(entity_pt.x, entity_pt.y)

      return new_pt
   def within_bounds(self, pt):
      return (pt.x >= 0 and pt.x < self.num_cols and
         pt.y >= 0 and pt.y < self.num_rows)

   def is_occupied(self, pt):
      return (self.within_bounds(pt) and
         self.occupancy.get_cell(pt) != None)
   def find_nearest(self, pt, type):
      oftype = [(e, distance_sq(pt, e.get_position())) # entities e?
         for e in self.entities if isinstance(e, type)]

      return nearest_entity(oftype)

   def add_entity(self, entity):
      pt = entity.get_position()
      if self.within_bounds(pt):
         old_entity = self.occupancy.get_cell(pt)
         if old_entity != None:
            old_entity.clear_pending_actions()
         self.occupancy.set_cell(pt, entity) # what to do with occ_grid? ... self.occupancy 
         self.entities.append(entity)

   def move_entity(self, entity, pt):
      tiles = []
      if self.within_bounds(pt):
         old_pt = pt.get_position() # not sure if this is right
         self.occupancy.set_cell( old_pt, None) 
         tiles.append(old_pt)
         self.occupancy.set_cell(pt, entity)
         tiles.append(pt)
         entity.set_position(pt) 

      return tiles

   def remove_entity(self, entity):
      self.remove_entity_at(entity.get_position())

   def remove_entity_at(self, pt):
      if (self.within_bounds(pt) and
         self.occupancy.get_cell(pt) != None):
         entity = self.occupancy.get_cell( pt)
         entity.set_position( point.Point(-1, -1))
         self.entities.remove(entity)
         self.occupancy.set_cell( pt, None)

   def schedule_action(self, action, time):
      self.action_queue.insert(action, time)

   def unschedule_action(self, action):
      self.action_queue.remove(action)

   def update_on_time(self, ticks):
      tiles = []

      next = self.action_queue.head()
      while next and next.ord < ticks:
         self.action_queue.pop()
         tiles.extend(next.item(ticks))  # invoke action function
         next = self.action_queue.head()

      return tiles

   def get_background_image(self, pt):
      if self.within_bounds( pt):
         return (self.background.get_cell(pt)).get_image() # pt.get_image?

   def get_background(self, pt):
      if self.within_bounds(pt):
         return self.background.get_cell(pt)

   def set_background(self, pt, bgnd):
      if self.within_bounds(pt):
         self.background.set_cell(pt, bgnd)

   def get_tile_occupant(self, pt):
      if self.within_bounds(pt):
         return self.occupancy.get_cell(pt)

   def get_entities(self):
      return self.entities
   
def distance_sq(p1, p2):
   return (p1.x - p2.x)**2 + (p1.y - p2.y)**2
   
def nearest_entity(entity_dists):
   if len(entity_dists) > 0:
      pair = entity_dists[0]
      for other in entity_dists:
         if other[1] < pair[1]:
            pair = other
      nearest = pair[0]
   else:
      nearest = None 
   return nearest
