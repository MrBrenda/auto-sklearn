# Data Analysis

Skill for exploratory data analysis (EDA) and dataset characterization for AutoML research.

## When to Use
When the user wants to analyze datasets, compute metafeatures, or understand data characteristics before running auto-sklearn.

## Instructions

1. **Basic EDA**: Perform standard exploratory data analysis.
   - Dataset shape, feature types (numerical, categorical, mixed)
   - Missing value analysis (percentage, patterns - MCAR/MAR/MNAR)
   - Class distribution for classification tasks (imbalance ratio)
   - Basic statistics: mean, std, min, max, quartiles per feature
   - Correlation analysis and feature redundancy detection

2. **Metafeature Computation**: Compute dataset metafeatures used by auto-sklearn's metalearning.
   ```python
   from autosklearn.metalearning.metafeatures import metafeatures

   # auto-sklearn computes metafeatures like:
   # - NumberOfInstances, NumberOfFeatures, NumberOfClasses
   # - ClassEntropy, MeanFeatureEntropy
   # - Skewness, Kurtosis statistics
   # - Landmark features (1-NN, decision stump performance)
   ```
   - Reference `autosklearn/metalearning/metafeatures/` for the full list
   - Compare metafeatures against the metadata database in `autosklearn/metalearning/files/`

3. **Data Quality Assessment**: Identify potential issues.
   - Detect outliers using IQR or isolation forest
   - Check for duplicate rows/features
   - Identify near-constant features (low variance)
   - Assess feature scaling requirements
   - Check for label noise indicators

4. **Dataset Comparison**: Compare datasets for research purposes.
   - Project datasets into metafeature space
   - Find similar datasets in OpenML for transfer learning
   - Visualize dataset similarities using t-SNE/UMAP on metafeatures
   - Identify dataset clusters for benchmark suite design

5. **Preprocessing Recommendations**: Suggest preprocessing based on analysis.
   - Recommend imputation strategies based on missing patterns
   - Suggest encoding methods for categorical features
   - Recommend rescaling based on feature distributions
   - Identify features that may benefit from transformation
