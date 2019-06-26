function [] = untitled(rowOfset, columnOfset, example, appType, calculatePercentage)
filePath = strcat('/home/orhan/EdgeCloudSim/sim_results','/ite1/','SIMRESULT_','SINGLE_TIER','_NEXT_FIT_','1000','DEVICES_',appType,'_GENERIC.log');
readData = dlmread(filePath,';',rowOfset,0);
value = readData(1,columnOfset);
disp(value)
filename = ['',example ' ',appType,'.csv'] ;
csvwrite(filename,value)
