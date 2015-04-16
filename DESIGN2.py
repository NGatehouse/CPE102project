Entities.py
   
   
   Entity #get_images get_image next_image get_name 
      Background(Entity)       
      On_Grid(Entity) # set_position , get_position
        Obstacle(On_Grid)   # add entity_string  ?????????????????
        Actor(On_Grid, Action_manager) 
         # get_rate add_pending_actions remove_pending_actions clear_pending_actions get_pending_actions 
            Ore(Actor) # create_ore_transform_action add entity_string  ?????????????????           
            Vein(Actor) # create_vein_action add entity_string  ?????????????????        
            Motion_actor(Actor,Animation_manager)  #possibly next_possition and blob_next_possition from worldmodel.py, create_entity_death_action, (maybe self_to_other) (maybe create_actor_action)
                OreBlob(Motion_actor) #blob_to_vein create_ore_blob_action
                Quake(Motion_actor) #redefine get_rate as pass ????????????
            Mining(Actor) #3 actors:  set_resource_count get_resource_count set_resource_limit               
                 Blacksmith(Mining) 
                 Miner(Mining,Motion_actor) #create_miner_action try_transform_miner, add entity string ??
                     MinerNotFull(Miner) #try_transform_miner_not_full miner_to_ore create_miner_not_full_action
                     MinerFull(Miner) #try_transform_miner_full miner_to_smith
   Action_manager #scheduling class, schedule_action, 
      Animation_manager(Action_manager) # schedule_animation create_animation_action   get_animation_rate
            
                
# Question 1... maybe self_to_other method for motion actor instead of having separate methods.. i.e. (blob_to_vein,miner_to_ore,miner_to_smith)       
# Question 2... add entity_string to 4 different classes.. Obstacle, Ore, Vein, Miner[full & notfull same thing?? since they are a "miner"] 
# Question 3... for Quake should we override get_rate? (pass)
# Question 4... instead of create_ore_blob_action and create_miner_action... should it be create_actor_action (in motion_actor)



# uml drawing obj oriented designs.. software engineer classes talk about... gets heavy weight, use just to draw. unified modeling language
#next position in method for actor

         
