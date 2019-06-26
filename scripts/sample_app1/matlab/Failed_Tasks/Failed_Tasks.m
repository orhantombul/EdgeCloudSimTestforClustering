health_app=csvread('HEALTH_APP.csv');
aug_app=csvread('AUGMENTED_REALITY.csv');
heavy_comp=csvread('HEAVY_COMP_APP.csv');
info_app =csvread('INFOTAINMENT_APP.csv');

col5 = info_app(:,1);
col3 = aug_app(:,1);
col1 = health_app(:,1);
col4 = heavy_comp(:,1);
col2 = [100,200,300,400,500,600,700,800,900,1000];

plot(col2,col1)
title('Combine Plots')
ylabel('Failed Tasks')
xlabel('Number of Mobile Devices')

x='Failed_Tasks';

hold on 

plot(col2,col3)
plot(col2,col4)
plot(col2,col5)

hold off
legend('healthapp','augapp','heavycomp','infoapp')

filename = strcat(' ',x);
saveas(gcf, filename, 'pdf');