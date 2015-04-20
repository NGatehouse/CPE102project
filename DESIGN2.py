Entities.py   
   Entity #get_images  get_image  next_image  get_name 
      Background(Entity)       
      On_Grid(Entity) # set_position  get_position
        Quake(On_Grid, animation_manager, action_manger) 
        Obstacle(On_Grid)    
        Actor(On_Grid,action_manger) 
         # get_rate 
            Ore(Actor) # create_ore_transform_action               
            Vein(Actor) # create_vein_action            
            OreBlob(Actor,animation_manager) #create_ore_blob_action
            Mining(Actor) '''Interface'''#set_resource_count get_resource_count  set_resource_limit   
                 Blacksmith(Mining) 
                 Miner(Mining,Motion_actor, animation_manager) #create_miner_action  try_transform_miner animation_rate   
                     MinerNotFull(Miner) #try_transform_miner_not_full   create_miner_not_full_action
                     MinerFull(Miner) #try_transform_miner_full   
   Action_manager '''Interface'''#scheduling class, schedule_action, 
                  #add_pending_actions remove_pending_actions clear_pending_actions get_pending_actions  , create_entity_death_action
   Animation_manager '''Interface'''# schedule_animation create_animation_action   get_animation_rate  
      
      

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



         
