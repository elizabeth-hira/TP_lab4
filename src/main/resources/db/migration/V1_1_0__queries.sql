select *
from retiree
where retirement_experience > 5;

select retiree.id, surname, name, retirement_experience, job.job_position
from retiree
         left join job
                   on retiree.job_id = job.id;

select count(*)
from retiree
where retirement_experience=2;

select sum (retirement)
from retiree
where retirement_experience > 10;

select max(retirement)
from retiree;

select min(retirement)
from retiree;

select retiree.*, job.job_position
from retiree inner join job
                        on retiree.job_id = job.id
where job_position = 'teachers higher category';
