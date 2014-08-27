/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/

package ch.admin.isb.hermes5.epf.uma.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.admin.isb.hermes5.epf.uma.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MethodLibrary_QNAME = new QName("http://www.eclipse.org/epf/uma/1.0.6", "MethodLibrary");
    private final static QName _MethodPlugin_QNAME = new QName("http://www.eclipse.org/epf/uma/1.0.6", "MethodPlugin");
    private final static QName _MethodConfiguration_QNAME = new QName("http://www.eclipse.org/epf/uma/1.0.6", "MethodConfiguration");
    private final static QName _WorkProductDescriptorOptionalInputTo_QNAME = new QName("", "OptionalInputTo");
    private final static QName _WorkProductDescriptorDeliverableParts_QNAME = new QName("", "DeliverableParts");
    private final static QName _WorkProductDescriptorImpacts_QNAME = new QName("", "Impacts");
    private final static QName _WorkProductDescriptorMandatoryInputTo_QNAME = new QName("", "MandatoryInputTo");
    private final static QName _WorkProductDescriptorExternalInputTo_QNAME = new QName("", "ExternalInputTo");
    private final static QName _WorkProductDescriptorOutputFrom_QNAME = new QName("", "OutputFrom");
    private final static QName _WorkProductDescriptorImpactedBy_QNAME = new QName("", "ImpactedBy");
    private final static QName _TeamProfileSubTeam_QNAME = new QName("", "SubTeam");
    private final static QName _TeamProfileSuperTeam_QNAME = new QName("", "SuperTeam");
    private final static QName _TeamProfileRole_QNAME = new QName("", "Role");
    private final static QName _WorkProductEstimate_QNAME = new QName("", "Estimate");
    private final static QName _WorkProductTemplate_QNAME = new QName("", "Template");
    private final static QName _WorkProductToolMentor_QNAME = new QName("", "ToolMentor");
    private final static QName _WorkProductReport_QNAME = new QName("", "Report");
    private final static QName _WorkProductEstimationConsiderations_QNAME = new QName("", "EstimationConsiderations");
    private final static QName _BreakdownElementSupportingMaterial_QNAME = new QName("", "SupportingMaterial");
    private final static QName _BreakdownElementWhitepaper_QNAME = new QName("", "Whitepaper");
    private final static QName _BreakdownElementExample_QNAME = new QName("", "Example");
    private final static QName _BreakdownElementReusableAsset_QNAME = new QName("", "ReusableAsset");
    private final static QName _BreakdownElementChecklist_QNAME = new QName("", "Checklist");
    private final static QName _BreakdownElementGuideline_QNAME = new QName("", "Guideline");
    private final static QName _BreakdownElementConcept_QNAME = new QName("", "Concept");
    private final static QName _DisciplineReferenceWorkflow_QNAME = new QName("", "ReferenceWorkflow");
    private final static QName _DisciplineTask_QNAME = new QName("", "Task");
    private final static QName _DisciplineSubDiscipline_QNAME = new QName("", "SubDiscipline");
    private final static QName _TaskDescriptorMandatoryInput_QNAME = new QName("", "MandatoryInput");
    private final static QName _TaskDescriptorOptionalInput_QNAME = new QName("", "OptionalInput");
    private final static QName _TaskDescriptorAssistedBy_QNAME = new QName("", "AssistedBy");
    private final static QName _TaskDescriptorPerformedPrimarilyBy_QNAME = new QName("", "PerformedPrimarilyBy");
    private final static QName _TaskDescriptorExternalInput_QNAME = new QName("", "ExternalInput");
    private final static QName _TaskDescriptorOutput_QNAME = new QName("", "Output");
    private final static QName _TaskDescriptorAdditionallyPerformedBy_QNAME = new QName("", "AdditionallyPerformedBy");
    private final static QName _EstimateEstimationMetric_QNAME = new QName("", "EstimationMetric");
    private final static QName _CustomCategoryCategorizedElement_QNAME = new QName("", "CategorizedElement");
    private final static QName _CustomCategorySubCategory_QNAME = new QName("", "SubCategory");
    private final static QName _PracticeSubPractice_QNAME = new QName("", "SubPractice");
    private final static QName _PracticeActivityReference_QNAME = new QName("", "ActivityReference");
    private final static QName _PracticeContentReference_QNAME = new QName("", "ContentReference");
    private final static QName _DeliveryProcessEducationMaterial_QNAME = new QName("", "EducationMaterial");
    private final static QName _DeliveryProcessCommunicationsMaterial_QNAME = new QName("", "CommunicationsMaterial");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.admin.isb.hermes5.epf.uma.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MethodPlugin }
     * 
     */
    public MethodPlugin createMethodPlugin() {
        return new MethodPlugin();
    }

    /**
     * Create an instance of {@link MethodConfiguration }
     * 
     */
    public MethodConfiguration createMethodConfiguration() {
        return new MethodConfiguration();
    }

    /**
     * Create an instance of {@link MethodLibrary }
     * 
     */
    public MethodLibrary createMethodLibrary() {
        return new MethodLibrary();
    }

    /**
     * Create an instance of {@link PlanningData }
     * 
     */
    public PlanningData createPlanningData() {
        return new PlanningData();
    }

    /**
     * Create an instance of {@link TeamProfile }
     * 
     */
    public TeamProfile createTeamProfile() {
        return new TeamProfile();
    }

    /**
     * Create an instance of {@link Tool }
     * 
     */
    public Tool createTool() {
        return new Tool();
    }

    /**
     * Create an instance of {@link ReusableAsset }
     * 
     */
    public ReusableAsset createReusableAsset() {
        return new ReusableAsset();
    }

    /**
     * Create an instance of {@link Deliverable }
     * 
     */
    public Deliverable createDeliverable() {
        return new Deliverable();
    }

    /**
     * Create an instance of {@link EstimationConsiderations }
     * 
     */
    public EstimationConsiderations createEstimationConsiderations() {
        return new EstimationConsiderations();
    }

    /**
     * Create an instance of {@link TaskDescriptor }
     * 
     */
    public TaskDescriptor createTaskDescriptor() {
        return new TaskDescriptor();
    }

    /**
     * Create an instance of {@link WorkOrder }
     * 
     */
    public WorkOrder createWorkOrder() {
        return new WorkOrder();
    }

    /**
     * Create an instance of {@link DisciplineGrouping }
     * 
     */
    public DisciplineGrouping createDisciplineGrouping() {
        return new DisciplineGrouping();
    }

    /**
     * Create an instance of {@link WorkProductType }
     * 
     */
    public WorkProductType createWorkProductType() {
        return new WorkProductType();
    }

    /**
     * Create an instance of {@link Kind }
     * 
     */
    public Kind createKind() {
        return new Kind();
    }

    /**
     * Create an instance of {@link NamedElement }
     * 
     */
    public NamedElement createNamedElement() {
        return new NamedElement();
    }

    /**
     * Create an instance of {@link Milestone }
     * 
     */
    public Milestone createMilestone() {
        return new Milestone();
    }

    /**
     * Create an instance of {@link WorkProductDescriptor }
     * 
     */
    public WorkProductDescriptor createWorkProductDescriptor() {
        return new WorkProductDescriptor();
    }

    /**
     * Create an instance of {@link CompositeRole }
     * 
     */
    public CompositeRole createCompositeRole() {
        return new CompositeRole();
    }

    /**
     * Create an instance of {@link ArtifactDescription }
     * 
     */
    public ArtifactDescription createArtifactDescription() {
        return new ArtifactDescription();
    }

    /**
     * Create an instance of {@link ProcessPackage }
     * 
     */
    public ProcessPackage createProcessPackage() {
        return new ProcessPackage();
    }

    /**
     * Create an instance of {@link Process }
     * 
     */
    public Process createProcess() {
        return new Process();
    }

    /**
     * Create an instance of {@link ProcessPlanningTemplate }
     * 
     */
    public ProcessPlanningTemplate createProcessPlanningTemplate() {
        return new ProcessPlanningTemplate();
    }

    /**
     * Create an instance of {@link CapabilityPattern }
     * 
     */
    public CapabilityPattern createCapabilityPattern() {
        return new CapabilityPattern();
    }

    /**
     * Create an instance of {@link WorkBreakdownElement }
     * 
     */
    public WorkBreakdownElement createWorkBreakdownElement() {
        return new WorkBreakdownElement();
    }

    /**
     * Create an instance of {@link ProcessDescription }
     * 
     */
    public ProcessDescription createProcessDescription() {
        return new ProcessDescription();
    }

    /**
     * Create an instance of {@link DeliveryProcessDescription }
     * 
     */
    public DeliveryProcessDescription createDeliveryProcessDescription() {
        return new DeliveryProcessDescription();
    }

    /**
     * Create an instance of {@link Domain }
     * 
     */
    public Domain createDomain() {
        return new Domain();
    }

    /**
     * Create an instance of {@link Checklist }
     * 
     */
    public Checklist createChecklist() {
        return new Checklist();
    }

    /**
     * Create an instance of {@link DescribableElement }
     * 
     */
    public DescribableElement createDescribableElement() {
        return new DescribableElement();
    }

    /**
     * Create an instance of {@link MethodElement }
     * 
     */
    public MethodElement createMethodElement() {
        return new MethodElement();
    }

    /**
     * Create an instance of {@link ProcessElement }
     * 
     */
    public ProcessElement createProcessElement() {
        return new ProcessElement();
    }

    /**
     * Create an instance of {@link DescriptorDescription }
     * 
     */
    public DescriptorDescription createDescriptorDescription() {
        return new DescriptorDescription();
    }

    /**
     * Create an instance of {@link Whitepaper }
     * 
     */
    public Whitepaper createWhitepaper() {
        return new Whitepaper();
    }

    /**
     * Create an instance of {@link BreakdownElement }
     * 
     */
    public BreakdownElement createBreakdownElement() {
        return new BreakdownElement();
    }

    /**
     * Create an instance of {@link WorkDefinition }
     * 
     */
    public WorkDefinition createWorkDefinition() {
        return new WorkDefinition();
    }

    /**
     * Create an instance of {@link Activity }
     * 
     */
    public Activity createActivity() {
        return new Activity();
    }

    /**
     * Create an instance of {@link Artifact }
     * 
     */
    public Artifact createArtifact() {
        return new Artifact();
    }

    /**
     * Create an instance of {@link Task }
     * 
     */
    public Task createTask() {
        return new Task();
    }

    /**
     * Create an instance of {@link ToolMentor }
     * 
     */
    public ToolMentor createToolMentor() {
        return new ToolMentor();
    }

    /**
     * Create an instance of {@link Phase }
     * 
     */
    public Phase createPhase() {
        return new Phase();
    }

    /**
     * Create an instance of {@link GuidanceDescription }
     * 
     */
    public GuidanceDescription createGuidanceDescription() {
        return new GuidanceDescription();
    }

    /**
     * Create an instance of {@link CustomCategory }
     * 
     */
    public CustomCategory createCustomCategory() {
        return new CustomCategory();
    }

    /**
     * Create an instance of {@link TaskDescription }
     * 
     */
    public TaskDescription createTaskDescription() {
        return new TaskDescription();
    }

    /**
     * Create an instance of {@link ProcessComponent }
     * 
     */
    public ProcessComponent createProcessComponent() {
        return new ProcessComponent();
    }

    /**
     * Create an instance of {@link BreakdownElementDescription }
     * 
     */
    public BreakdownElementDescription createBreakdownElementDescription() {
        return new BreakdownElementDescription();
    }

    /**
     * Create an instance of {@link EstimatingMetric }
     * 
     */
    public EstimatingMetric createEstimatingMetric() {
        return new EstimatingMetric();
    }

    /**
     * Create an instance of {@link Iteration }
     * 
     */
    public Iteration createIteration() {
        return new Iteration();
    }

    /**
     * Create an instance of {@link Guideline }
     * 
     */
    public Guideline createGuideline() {
        return new Guideline();
    }

    /**
     * Create an instance of {@link PackageableElement }
     * 
     */
    public PackageableElement createPackageableElement() {
        return new PackageableElement();
    }

    /**
     * Create an instance of {@link Constraint }
     * 
     */
    public Constraint createConstraint() {
        return new Constraint();
    }

    /**
     * Create an instance of {@link Report }
     * 
     */
    public Report createReport() {
        return new Report();
    }

    /**
     * Create an instance of {@link Practice }
     * 
     */
    public Practice createPractice() {
        return new Practice();
    }

    /**
     * Create an instance of {@link Discipline }
     * 
     */
    public Discipline createDiscipline() {
        return new Discipline();
    }

    /**
     * Create an instance of {@link ContentPackage }
     * 
     */
    public ContentPackage createContentPackage() {
        return new ContentPackage();
    }

    /**
     * Create an instance of {@link MethodPackage }
     * 
     */
    public MethodPackage createMethodPackage() {
        return new MethodPackage();
    }

    /**
     * Create an instance of {@link Descriptor }
     * 
     */
    public Descriptor createDescriptor() {
        return new Descriptor();
    }

    /**
     * Create an instance of {@link ApplicableMetaClassInfo }
     * 
     */
    public ApplicableMetaClassInfo createApplicableMetaClassInfo() {
        return new ApplicableMetaClassInfo();
    }

    /**
     * Create an instance of {@link MethodElementProperty }
     * 
     */
    public MethodElementProperty createMethodElementProperty() {
        return new MethodElementProperty();
    }

    /**
     * Create an instance of {@link Example }
     * 
     */
    public Example createExample() {
        return new Example();
    }

    /**
     * Create an instance of {@link Roadmap }
     * 
     */
    public Roadmap createRoadmap() {
        return new Roadmap();
    }

    /**
     * Create an instance of {@link ProcessComponentInterface }
     * 
     */
    public ProcessComponentInterface createProcessComponentInterface() {
        return new ProcessComponentInterface();
    }

    /**
     * Create an instance of {@link Guidance }
     * 
     */
    public Guidance createGuidance() {
        return new Guidance();
    }

    /**
     * Create an instance of {@link ContentCategory }
     * 
     */
    public ContentCategory createContentCategory() {
        return new ContentCategory();
    }

    /**
     * Create an instance of {@link RoleDescriptor }
     * 
     */
    public RoleDescriptor createRoleDescriptor() {
        return new RoleDescriptor();
    }

    /**
     * Create an instance of {@link ContentElement }
     * 
     */
    public ContentElement createContentElement() {
        return new ContentElement();
    }

    /**
     * Create an instance of {@link MethodUnit }
     * 
     */
    public MethodUnit createMethodUnit() {
        return new MethodUnit();
    }

    /**
     * Create an instance of {@link Estimate }
     * 
     */
    public Estimate createEstimate() {
        return new Estimate();
    }

    /**
     * Create an instance of {@link ActivityDescription }
     * 
     */
    public ActivityDescription createActivityDescription() {
        return new ActivityDescription();
    }

    /**
     * Create an instance of {@link Template }
     * 
     */
    public Template createTemplate() {
        return new Template();
    }

    /**
     * Create an instance of {@link RoleSet }
     * 
     */
    public RoleSet createRoleSet() {
        return new RoleSet();
    }

    /**
     * Create an instance of {@link Concept }
     * 
     */
    public Concept createConcept() {
        return new Concept();
    }

    /**
     * Create an instance of {@link DeliveryProcess }
     * 
     */
    public DeliveryProcess createDeliveryProcess() {
        return new DeliveryProcess();
    }

    /**
     * Create an instance of {@link Outcome }
     * 
     */
    public Outcome createOutcome() {
        return new Outcome();
    }

    /**
     * Create an instance of {@link ContentCategoryPackage }
     * 
     */
    public ContentCategoryPackage createContentCategoryPackage() {
        return new ContentCategoryPackage();
    }

    /**
     * Create an instance of {@link SupportingMaterial }
     * 
     */
    public SupportingMaterial createSupportingMaterial() {
        return new SupportingMaterial();
    }

    /**
     * Create an instance of {@link Section }
     * 
     */
    public Section createSection() {
        return new Section();
    }

    /**
     * Create an instance of {@link TermDefinition }
     * 
     */
    public TermDefinition createTermDefinition() {
        return new TermDefinition();
    }

    /**
     * Create an instance of {@link RoleSetGrouping }
     * 
     */
    public RoleSetGrouping createRoleSetGrouping() {
        return new RoleSetGrouping();
    }

    /**
     * Create an instance of {@link DeliverableDescription }
     * 
     */
    public DeliverableDescription createDeliverableDescription() {
        return new DeliverableDescription();
    }

    /**
     * Create an instance of {@link PracticeDescription }
     * 
     */
    public PracticeDescription createPracticeDescription() {
        return new PracticeDescription();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link ContentDescription }
     * 
     */
    public ContentDescription createContentDescription() {
        return new ContentDescription();
    }

    /**
     * Create an instance of {@link WorkProduct }
     * 
     */
    public WorkProduct createWorkProduct() {
        return new WorkProduct();
    }

    /**
     * Create an instance of {@link RoleDescription }
     * 
     */
    public RoleDescription createRoleDescription() {
        return new RoleDescription();
    }

    /**
     * Create an instance of {@link Element }
     * 
     */
    public Element createElement() {
        return new Element();
    }

    /**
     * Create an instance of {@link WorkProductDescription }
     * 
     */
    public WorkProductDescription createWorkProductDescription() {
        return new WorkProductDescription();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethodLibrary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eclipse.org/epf/uma/1.0.6", name = "MethodLibrary")
    public JAXBElement<MethodLibrary> createMethodLibrary(MethodLibrary value) {
        return new JAXBElement<MethodLibrary>(_MethodLibrary_QNAME, MethodLibrary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethodPlugin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eclipse.org/epf/uma/1.0.6", name = "MethodPlugin")
    public JAXBElement<MethodPlugin> createMethodPlugin(MethodPlugin value) {
        return new JAXBElement<MethodPlugin>(_MethodPlugin_QNAME, MethodPlugin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethodConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eclipse.org/epf/uma/1.0.6", name = "MethodConfiguration")
    public JAXBElement<MethodConfiguration> createMethodConfiguration(MethodConfiguration value) {
        return new JAXBElement<MethodConfiguration>(_MethodConfiguration_QNAME, MethodConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OptionalInputTo", scope = WorkProductDescriptor.class)
    public JAXBElement<String> createWorkProductDescriptorOptionalInputTo(String value) {
        return new JAXBElement<String>(_WorkProductDescriptorOptionalInputTo_QNAME, String.class, WorkProductDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DeliverableParts", scope = WorkProductDescriptor.class)
    public JAXBElement<String> createWorkProductDescriptorDeliverableParts(String value) {
        return new JAXBElement<String>(_WorkProductDescriptorDeliverableParts_QNAME, String.class, WorkProductDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Impacts", scope = WorkProductDescriptor.class)
    public JAXBElement<String> createWorkProductDescriptorImpacts(String value) {
        return new JAXBElement<String>(_WorkProductDescriptorImpacts_QNAME, String.class, WorkProductDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MandatoryInputTo", scope = WorkProductDescriptor.class)
    public JAXBElement<String> createWorkProductDescriptorMandatoryInputTo(String value) {
        return new JAXBElement<String>(_WorkProductDescriptorMandatoryInputTo_QNAME, String.class, WorkProductDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ExternalInputTo", scope = WorkProductDescriptor.class)
    public JAXBElement<String> createWorkProductDescriptorExternalInputTo(String value) {
        return new JAXBElement<String>(_WorkProductDescriptorExternalInputTo_QNAME, String.class, WorkProductDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OutputFrom", scope = WorkProductDescriptor.class)
    public JAXBElement<String> createWorkProductDescriptorOutputFrom(String value) {
        return new JAXBElement<String>(_WorkProductDescriptorOutputFrom_QNAME, String.class, WorkProductDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ImpactedBy", scope = WorkProductDescriptor.class)
    public JAXBElement<String> createWorkProductDescriptorImpactedBy(String value) {
        return new JAXBElement<String>(_WorkProductDescriptorImpactedBy_QNAME, String.class, WorkProductDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SubTeam", scope = TeamProfile.class)
    public JAXBElement<String> createTeamProfileSubTeam(String value) {
        return new JAXBElement<String>(_TeamProfileSubTeam_QNAME, String.class, TeamProfile.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SuperTeam", scope = TeamProfile.class)
    public JAXBElement<String> createTeamProfileSuperTeam(String value) {
        return new JAXBElement<String>(_TeamProfileSuperTeam_QNAME, String.class, TeamProfile.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Role", scope = TeamProfile.class)
    public JAXBElement<String> createTeamProfileRole(String value) {
        return new JAXBElement<String>(_TeamProfileRole_QNAME, String.class, TeamProfile.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Estimate", scope = WorkProduct.class)
    public JAXBElement<String> createWorkProductEstimate(String value) {
        return new JAXBElement<String>(_WorkProductEstimate_QNAME, String.class, WorkProduct.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Template", scope = WorkProduct.class)
    public JAXBElement<String> createWorkProductTemplate(String value) {
        return new JAXBElement<String>(_WorkProductTemplate_QNAME, String.class, WorkProduct.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ToolMentor", scope = WorkProduct.class)
    public JAXBElement<String> createWorkProductToolMentor(String value) {
        return new JAXBElement<String>(_WorkProductToolMentor_QNAME, String.class, WorkProduct.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Report", scope = WorkProduct.class)
    public JAXBElement<String> createWorkProductReport(String value) {
        return new JAXBElement<String>(_WorkProductReport_QNAME, String.class, WorkProduct.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EstimationConsiderations", scope = WorkProduct.class)
    public JAXBElement<String> createWorkProductEstimationConsiderations(String value) {
        return new JAXBElement<String>(_WorkProductEstimationConsiderations_QNAME, String.class, WorkProduct.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SupportingMaterial", scope = BreakdownElement.class)
    public JAXBElement<String> createBreakdownElementSupportingMaterial(String value) {
        return new JAXBElement<String>(_BreakdownElementSupportingMaterial_QNAME, String.class, BreakdownElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Whitepaper", scope = BreakdownElement.class)
    public JAXBElement<String> createBreakdownElementWhitepaper(String value) {
        return new JAXBElement<String>(_BreakdownElementWhitepaper_QNAME, String.class, BreakdownElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Example", scope = BreakdownElement.class)
    public JAXBElement<String> createBreakdownElementExample(String value) {
        return new JAXBElement<String>(_BreakdownElementExample_QNAME, String.class, BreakdownElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ReusableAsset", scope = BreakdownElement.class)
    public JAXBElement<String> createBreakdownElementReusableAsset(String value) {
        return new JAXBElement<String>(_BreakdownElementReusableAsset_QNAME, String.class, BreakdownElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Checklist", scope = BreakdownElement.class)
    public JAXBElement<String> createBreakdownElementChecklist(String value) {
        return new JAXBElement<String>(_BreakdownElementChecklist_QNAME, String.class, BreakdownElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Guideline", scope = BreakdownElement.class)
    public JAXBElement<String> createBreakdownElementGuideline(String value) {
        return new JAXBElement<String>(_BreakdownElementGuideline_QNAME, String.class, BreakdownElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Concept", scope = BreakdownElement.class)
    public JAXBElement<String> createBreakdownElementConcept(String value) {
        return new JAXBElement<String>(_BreakdownElementConcept_QNAME, String.class, BreakdownElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ReferenceWorkflow", scope = Discipline.class)
    public JAXBElement<String> createDisciplineReferenceWorkflow(String value) {
        return new JAXBElement<String>(_DisciplineReferenceWorkflow_QNAME, String.class, Discipline.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Task", scope = Discipline.class)
    public JAXBElement<String> createDisciplineTask(String value) {
        return new JAXBElement<String>(_DisciplineTask_QNAME, String.class, Discipline.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Discipline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SubDiscipline", scope = Discipline.class)
    public JAXBElement<Discipline> createDisciplineSubDiscipline(Discipline value) {
        return new JAXBElement<Discipline>(_DisciplineSubDiscipline_QNAME, Discipline.class, Discipline.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MandatoryInput", scope = TaskDescriptor.class)
    public JAXBElement<String> createTaskDescriptorMandatoryInput(String value) {
        return new JAXBElement<String>(_TaskDescriptorMandatoryInput_QNAME, String.class, TaskDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OptionalInput", scope = TaskDescriptor.class)
    public JAXBElement<String> createTaskDescriptorOptionalInput(String value) {
        return new JAXBElement<String>(_TaskDescriptorOptionalInput_QNAME, String.class, TaskDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AssistedBy", scope = TaskDescriptor.class)
    public JAXBElement<String> createTaskDescriptorAssistedBy(String value) {
        return new JAXBElement<String>(_TaskDescriptorAssistedBy_QNAME, String.class, TaskDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PerformedPrimarilyBy", scope = TaskDescriptor.class)
    public JAXBElement<String> createTaskDescriptorPerformedPrimarilyBy(String value) {
        return new JAXBElement<String>(_TaskDescriptorPerformedPrimarilyBy_QNAME, String.class, TaskDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ExternalInput", scope = TaskDescriptor.class)
    public JAXBElement<String> createTaskDescriptorExternalInput(String value) {
        return new JAXBElement<String>(_TaskDescriptorExternalInput_QNAME, String.class, TaskDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Output", scope = TaskDescriptor.class)
    public JAXBElement<String> createTaskDescriptorOutput(String value) {
        return new JAXBElement<String>(_TaskDescriptorOutput_QNAME, String.class, TaskDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AdditionallyPerformedBy", scope = TaskDescriptor.class)
    public JAXBElement<String> createTaskDescriptorAdditionallyPerformedBy(String value) {
        return new JAXBElement<String>(_TaskDescriptorAdditionallyPerformedBy_QNAME, String.class, TaskDescriptor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EstimationConsiderations", scope = Estimate.class)
    public JAXBElement<String> createEstimateEstimationConsiderations(String value) {
        return new JAXBElement<String>(_WorkProductEstimationConsiderations_QNAME, String.class, Estimate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EstimationMetric", scope = Estimate.class)
    public JAXBElement<String> createEstimateEstimationMetric(String value) {
        return new JAXBElement<String>(_EstimateEstimationMetric_QNAME, String.class, Estimate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CategorizedElement", scope = CustomCategory.class)
    public JAXBElement<String> createCustomCategoryCategorizedElement(String value) {
        return new JAXBElement<String>(_CustomCategoryCategorizedElement_QNAME, String.class, CustomCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SubCategory", scope = CustomCategory.class)
    public JAXBElement<String> createCustomCategorySubCategory(String value) {
        return new JAXBElement<String>(_CustomCategorySubCategory_QNAME, String.class, CustomCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MandatoryInput", scope = Task.class)
    public JAXBElement<String> createTaskMandatoryInput(String value) {
        return new JAXBElement<String>(_TaskDescriptorMandatoryInput_QNAME, String.class, Task.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Estimate", scope = Task.class)
    public JAXBElement<String> createTaskEstimate(String value) {
        return new JAXBElement<String>(_WorkProductEstimate_QNAME, String.class, Task.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OptionalInput", scope = Task.class)
    public JAXBElement<String> createTaskOptionalInput(String value) {
        return new JAXBElement<String>(_TaskDescriptorOptionalInput_QNAME, String.class, Task.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ToolMentor", scope = Task.class)
    public JAXBElement<String> createTaskToolMentor(String value) {
        return new JAXBElement<String>(_WorkProductToolMentor_QNAME, String.class, Task.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Output", scope = Task.class)
    public JAXBElement<String> createTaskOutput(String value) {
        return new JAXBElement<String>(_TaskDescriptorOutput_QNAME, String.class, Task.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EstimationConsiderations", scope = Task.class)
    public JAXBElement<String> createTaskEstimationConsiderations(String value) {
        return new JAXBElement<String>(_WorkProductEstimationConsiderations_QNAME, String.class, Task.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AdditionallyPerformedBy", scope = Task.class)
    public JAXBElement<String> createTaskAdditionallyPerformedBy(String value) {
        return new JAXBElement<String>(_TaskDescriptorAdditionallyPerformedBy_QNAME, String.class, Task.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Practice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SubPractice", scope = Practice.class)
    public JAXBElement<Practice> createPracticeSubPractice(Practice value) {
        return new JAXBElement<Practice>(_PracticeSubPractice_QNAME, Practice.class, Practice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ActivityReference", scope = Practice.class)
    public JAXBElement<String> createPracticeActivityReference(String value) {
        return new JAXBElement<String>(_PracticeActivityReference_QNAME, String.class, Practice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ContentReference", scope = Practice.class)
    public JAXBElement<String> createPracticeContentReference(String value) {
        return new JAXBElement<String>(_PracticeContentReference_QNAME, String.class, Practice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EducationMaterial", scope = DeliveryProcess.class)
    public JAXBElement<String> createDeliveryProcessEducationMaterial(String value) {
        return new JAXBElement<String>(_DeliveryProcessEducationMaterial_QNAME, String.class, DeliveryProcess.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CommunicationsMaterial", scope = DeliveryProcess.class)
    public JAXBElement<String> createDeliveryProcessCommunicationsMaterial(String value) {
        return new JAXBElement<String>(_DeliveryProcessCommunicationsMaterial_QNAME, String.class, DeliveryProcess.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SupportingMaterial", scope = ContentElement.class)
    public JAXBElement<String> createContentElementSupportingMaterial(String value) {
        return new JAXBElement<String>(_BreakdownElementSupportingMaterial_QNAME, String.class, ContentElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Whitepaper", scope = ContentElement.class)
    public JAXBElement<String> createContentElementWhitepaper(String value) {
        return new JAXBElement<String>(_BreakdownElementWhitepaper_QNAME, String.class, ContentElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Example", scope = ContentElement.class)
    public JAXBElement<String> createContentElementExample(String value) {
        return new JAXBElement<String>(_BreakdownElementExample_QNAME, String.class, ContentElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ReusableAsset", scope = ContentElement.class)
    public JAXBElement<String> createContentElementReusableAsset(String value) {
        return new JAXBElement<String>(_BreakdownElementReusableAsset_QNAME, String.class, ContentElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Checklist", scope = ContentElement.class)
    public JAXBElement<String> createContentElementChecklist(String value) {
        return new JAXBElement<String>(_BreakdownElementChecklist_QNAME, String.class, ContentElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Guideline", scope = ContentElement.class)
    public JAXBElement<String> createContentElementGuideline(String value) {
        return new JAXBElement<String>(_BreakdownElementGuideline_QNAME, String.class, ContentElement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Concept", scope = ContentElement.class)
    public JAXBElement<String> createContentElementConcept(String value) {
        return new JAXBElement<String>(_BreakdownElementConcept_QNAME, String.class, ContentElement.class, value);
    }

}
