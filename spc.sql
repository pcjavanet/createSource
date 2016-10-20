　连续型
Only for study
　select serial_number, cparam1,  cparam2,  INT4( EXTRACT( EPOCH FROM (SELECT date_trunc('second',ts)))) as "a_DateTime" ,part_number
			 FROM spc.v_serial_activity_defect  where 
		//  process_name =  monitor.getMonitorShortName() 
		//  workstation =  monitor.getMonitorShortName() 
	   		//AND family_id=  monitor.getPartFamilyName()	 
	 		//AND part_number=  monitor.getPartNumber()  
			 
	AND cparam1 in (   attribute  ) 
			  AND anet_userid=  monitor.getNetUserId() 
 
		  AND ts >to_timestamp(  lastGroupTime  );
		  ORDER BY  ts ASC limit 15000  
非连续型收集数据

  SELECT  action AS passed, INT4( EXTRACT( EPOCH FROM (SELECT date_trunc('second',ts)))) as 
"a_DateTime",location_key,part_number,serial_number, to_char(ts,'YYYY-MM-DD HH24:MI:SS') as dateTimeStr  
			 FROM   spc.v_serial_activity_defect  
			 WHERE (action=11 or action=12)  
			 
			 //   AND process_name = monitor.getMonitorShortName()  
			 //    AND workstation = monitor.getMonitorShortName() 
		 
			 
			//  AND family_id=  monitor.getPartFamilyName()	 			 
			// AND part_number=  monitor.getPartNumber() 
			 
			  AND anet_userid= monitor.getNetUserId()  
			 AND ts >to_timestamp(  lastGroupTime ) 
			 ORDER BY  ts  ASC LIMIT 15000  

取得缺陷点SQL
  SELECT serial_number,  INT4( EXTRACT( EPOCH FROM (SELECT date_trunc('second',ts)))) as "a_DateTime" ,defectseq,defectcode,defectqty /
		 FROM   spc.v_serial_activity_defect  ");
		  WHERE defectcode NOT LIKE 'B%'   AND action=41  
		 
		 // AND process_name ='" + monitor.getMonitorShortName() + "' ");		 
		//	  AND workstation = monitor.getMonitorShortName() 
		 

		//  AND family_id=  monitor.getPartFamilyName()  
		//  AND part_number=  monitor.getPartNumber() 
		 
	  AND anet_userid= monitor.getNetUserId()  
 
	  AND ts >to_timestamp(  lastGroupTime  )
	  AND ts <=to_timestamp(  lastDataTime )
	  ORDER BY ts  ASC  

Pareto
	   SELECT  defectqty,serial_number as s_serial_number, defectcode as sf_defectcode ,location_key as a_location_key , INT4( EXTRACT( EPOCH FROM (SELECT date_trunc('second',ts))))  as a_a_datetime	 
		   FROM spc.v_serial_activity_defect  
		   WHERE   defectcode NOT LIKE 'B%' AND action=41  AND  ts >to_timestamp( lastGroupTime ) 
		   AND 　workstation　
		    AND　partNumberType　
		 ORDER BY  ts ASC　

SELECT DISTINCT defect_code as defectcode ,description FROM "+CommonServerUtil.getSpcSchemaPrefix() + "vr_spc_defcode   WHERE 　　AND  defect_code IN ('xx','x1','xx2')　　


