Entities.py 
   Action_manager '''Interface''' #remove_pending_action  get_pending_action  clear_pending_actions  schedule_action  create_entity_death_action
   Animation_manager '''Interface''' #get_animation_rate  create_animation_action  schedule_animation  
   Entity #DATA name images
   #get_images  get_image  next_image  get_name 
      Background(Entity)        
      On_Grid(Entity) #DATA name images position
        #set_position  get_position 
        Quake(On_Grid, animation_manager, action_manger) #DATA name  position  images  animation_rate
        Obstacle(On_Grid) #name  position  images
        #entity_string
        Actor(On_Grid,action_manger) #DATA name position rate images
         #get_rate 
            Ore(Actor) #DATA name  position  images  rate (=5000)
            #entity_string  create_ore_transform_action
            Vein(Actor) #DATA name  rate  position  images  resource_distance (=1) 
            #create_actor_motion  entity_string  get_resource_distance             
            OreBlob(Actor,animation_manager) #DATA name  position  rate images  animation_rate
            #_to_other  create_actor_motion
            Mining(Actor) '''Interface'''#DATA name  resoure_limit  position rate  images
            #set_resource_count get_resource_count  set_resource_limit   
                 Blacksmith(Mining) #DATA name  position  images  resoure_limit  rate  resource_distance(defaulted to 1)
                 #get_resource_distance  entity_string
                 Miner(Mining, animation_manager) #DATA name resoure_limit  position  rate  images animation_rate
                 #create_miner_action  try_transform_miner entity_string   
                     MinerNotFull(Miner) #DATA name resoure_limit  position  rate  images animation_rate
                     #try_transform  to_other   create_actor_motion 
                     MinerFull(Miner) #DATA name resoure_limit  position  rate  images animation_rate
                     #try_transform  to_other   create_actor_motion 
   
      
_____________________________________________________________________   

                             QUESTIONS

entity_string #should be

#the last functions in actions.py (create and schedule)

get_resource_distance

transform_action
_____________________________________________________________________  

#What is the difference between    super(b,self).__init__(x)  and  A.__init__(self,x) 
   # We used the latter in MinerNotFull and MinerFull because super() raises “TypeError: must be type, not classobj”
     
#In Blacksmith resource_count is not in __init__. How does this work?      




      
# Question 1... maybe self_to_other method for motion actor instead of having separate methods.. i.e. (blob_to_vein,miner_to_ore,miner_to_smith)     
Yes  
# Question 2... add entity_string to 4 different classes.. Obstacle, Ore, Vein, Miner[full & notfull same thing?? since they are a "miner"] 
Elevate to actor class method handles calls from entities can't handle.
# Question 3... for Quake should we override get_rate? (pass)  
Not actor. Animates. Redesign. Don't do pass
# Question 4... instead of create_ore_blob_action and create_miner_action... should it be create_actor_action (in motion_actor) 
create_action  Yes. Inherit and specialize.

Elevate animation_manager out of action_manger ... fixes quake redesign prob

Allowed to redefine methods for specialization



         
