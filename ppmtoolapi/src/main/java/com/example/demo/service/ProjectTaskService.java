package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.BacklogRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository; 
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
		
		
		try {

			// ProjectTAsk should be added to a specific Project , project != null, backlog
			// exist
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			// set the backlog to the project Task
			projectTask.setBacklog(backlog);
			// we want our project to look like : IDPRO-1, IDPRO-2...
			Integer backlogSequence = backlog.getPTSequence();
			// Update backlog sequence
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			// Add Backlog sequence to ProjectTask
			projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			// setting default priority and status

			if (projectTask.getPriority() == null) {
				projectTask.setPriority(3);

			}
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TODO");
			}
			// Few changes done by me
			// ProjectTask projectTask2 = projectTaskRepository.save(projectTask);
//			backlogRepository.save(backlog);
			return projectTaskRepository.save(projectTask);

		} catch (Exception ex) {
			throw new ProjectNotFoundException("project not found");
		}
	}
	public Iterable<ProjectTask> findBacklogById(String id) {
		Project project = projectRepository.findByProjectIdentifier(id);
		if (project == null) {
			throw new ProjectNotFoundException("Project not found");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		return projectTaskRepository.findByProjectSequence(pt_id);
	}

}
